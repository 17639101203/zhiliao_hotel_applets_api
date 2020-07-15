package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.comment.commentparm.CommentParm;
import com.zhiliao.hotel.controller.comment.vo.CommentDetailVO;
import com.zhiliao.hotel.controller.comment.vo.CommentToPhpVO;
import com.zhiliao.hotel.controller.comment.vo.CommentVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.mapper.ZlCommentMapper;
import com.zhiliao.hotel.mapper.ZlTagMapper;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.service.ZlCommentService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.QiniuUtils;
import com.zhiliao.hotel.utils.UploadPhotoUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @Author: zyj
 * @Date: 2020/4/14 10:35
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCommentServiceImpl implements ZlCommentService {

    private static final Logger logger = LoggerFactory.getLogger(ZlCommentServiceImpl.class);

    @Autowired
    private ZlCommentMapper zlCommentMapper;

    @Autowired
    private ZlTagMapper zlTagMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer addComment(ZlComment zlComment) {
        return zlCommentMapper.addComment(zlComment);
    }

    @Override
    public Map<String, Object> addComment(Long userid, CommentParm commentParm, MultipartFile multipartFile) {

        Map<String, Object> map = new HashMap<>();

        if (commentParm.getCommentID() != -1) {
            ZlComment zlComment = new ZlComment();
            zlComment.setCommentid(commentParm.getCommentID());
            List<ZlComment> zlCommentList = zlCommentMapper.select(zlComment);
            ZlComment zlCommentOld = zlCommentList.get(0);
            StringBuffer imageurls = new StringBuffer();
            String imageurlOld = zlCommentOld.getImageurls();
//            String imgurl = UploadPhotoUtil.uploadPhotoUtil(multipartFile);
            String imgurl = UploadPhotoUtil.uploadPhotoUtil2(multipartFile);
            imageurls.append(imageurlOld).append("|").append(imgurl);
            zlCommentMapper.updateCommentImg(commentParm.getCommentID(), imageurls.toString());
            map.put("commentid", commentParm.getCommentID());
            return map;
        }

        ZlComment zlComment = new ZlComment();
        Integer nowtime = DateUtils.javaToPhpNowDateTime();   // 获取当前时间
        zlComment.setHotelid(commentParm.getHotelID());   //酒店ID
        zlComment.setUserid(userid);   //用户ID  根据token获取userId
        zlComment.setEvaluation((byte) commentParm.getEvaluation().intValue());   //评论等级 1好评 2中评 3差评
        zlComment.setTagids(commentParm.getTagIDs());   //评论标签ID 多个用丨隔开
        zlComment.setContent(commentParm.getContent());   //评论内容
        zlComment.setRoomid(commentParm.getRoomid());      // 房间id
        zlComment.setRoomnumber(commentParm.getRoomnumber());  // 房间号
        zlComment.setCreatedate(nowtime);  //添加时间
        zlComment.setUpdatedate(nowtime);  //更新时间

        //传入文件分析后，得到文件存放地址  key：filePathBase
//        ReturnString<List<String>> returnString = uploadFileController.uploadFile(multipartFiles);
//        List<String> list = returnString.getData();
//        StringBuffer Imgurls = new StringBuffer();
//        list.forEach(item -> {
//            Imgurls.append(item + "|");   // 遍历集合，生成图片地址，并用 | 隔开
//        });
        if (multipartFile != null) {
//            String imgurl = UploadPhotoUtil.uploadPhotoUtil(multipartFile);
            String imgurl = UploadPhotoUtil.uploadPhotoUtil2(multipartFile);
            zlComment.setImageurls(imgurl);  //图片地址
        }
        zlCommentMapper.insertSelective(zlComment);

        logger.info("点赞吐槽数据信息插入数据库完成,id:" + zlComment.getCommentid());
        // 推送消息
        CommentToPhpVO commentToPhpVO = zlCommentMapper.selectToPhp(zlComment.getCommentid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_CLEAN, cleanOrderVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_COMMENT);
        orderPhpSendVO.setMessage(commentToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_COMMENT, orderStr);
        logger.info("推送点赞吐槽数据信息到redis通知php后台人员完成,订单信息:" + commentToPhpVO);

        map.put("commentid", zlComment.getCommentid());
        return map;
    }

    @Override
    public List<Map<String, Object>> findTags(Integer hotelid) {
        return zlTagMapper.getTags(hotelid);
    }


    @Override
    public PageInfoResult<List<CommentVO>> findComments(Long userid, Integer pageNo, Integer pageSize, Integer hotelId) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<CommentVO> comments = zlCommentMapper.getComments(userid, hotelId);
        for (CommentVO comment : comments) {
            String[] tagids;
            if (StringUtils.isNoneBlank(comment.getTagids())) {
                if (comment.getTagids().contains("|")) {
                    // 拆解标签I
                    tagids = comment.getTagids().split("|");
                } else {
                    tagids = new String[1];
                    tagids[0] = comment.getTagids();
                }
                // 查询标签名
                comment.setTagname(zlTagMapper.getTagName(tagids));
            }
        }
        PageInfo<CommentVO> pageInfo = new PageInfo<>(comments);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public CommentDetailVO findComment(Long userid, Integer commentid) {
        CommentDetailVO commentDetailVO = zlCommentMapper.getComment(userid, commentid);
        String[] tagids;
        if (StringUtils.isNoneBlank(commentDetailVO.getTagids())) {
            if (commentDetailVO.getTagids().contains("|")) {
                // 拆解标签I
                tagids = commentDetailVO.getTagids().split("|");
            } else {
                tagids = new String[1];
                tagids[0] = commentDetailVO.getTagids();
            }
            // 查询标签名
            commentDetailVO.setTagname(zlTagMapper.getTagName(tagids));
        }
        List<String> imageurllist = new LinkedList<>();
        if (StringUtils.isNoneBlank(commentDetailVO.getImageurls())) {
            String imageurls = commentDetailVO.getImageurls();
            if (imageurls.contains("|")) {
                String[] imageurlArr = imageurls.split("\\|");
                for (int i = 0; i < imageurlArr.length; i++) {
                    imageurllist.add(imageurlArr[i]);
                }
            } else {
                imageurllist.add(imageurls);
            }
        }
        commentDetailVO.setImageurllist(imageurllist);
        // 修改为已读状态
        zlCommentMapper.changeReplyReadStatus(userid, commentid);
        return commentDetailVO;
    }

    @Override
    public Long waitAppraiseTotal(Long userid) {
        return zlCommentMapper.waitAppraiseTotal(userid);
    }

}
