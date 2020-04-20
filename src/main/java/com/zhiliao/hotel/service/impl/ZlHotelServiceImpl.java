package com.zhiliao.hotel.service.impl;



import com.google.common.collect.Lists;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisConstant;
import com.zhiliao.hotel.controller.hotel.in.ZlHotelIn;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlBannerService;
import com.zhiliao.hotel.service.ZlHotelService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.GsonUtils;
import com.zhiliao.hotel.utils.IPUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 酒店业务实现类
 *
 * @author chenrong
 * @created date 2020/4/10
 */
@Service
public class ZlHotelServiceImpl implements ZlHotelService {

    @Resource
    private ZlHotelMapper zlHotelMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Resource
    private ZlXcxMenuMapper zlXcxMenuMapper;

    @Resource
    private ZlUserloginlogMapper zlUserloginlogMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private ZlWxuserMapper zlWxuserMapper;

    @Resource
    private com.zhiliao.hotel.mapper.zlNewsMapper zlNewsMapper;

    @Resource
    private ZlBannerService zlBannerService;


    @Override
    public ReturnString getById(String hotelId, String roomId, String token) throws ParseException {
        List<ZlXcxmenu> zlXcxMenuList = null;
        ZlHotelroom zlHotelroom = null;
        String state="1";
        if(state.equals("1")){
        if (!StringUtils.isEmpty(roomId)) {
            zlHotelroom = zlHotelRoomMapper.getById(roomId, hotelId);
            ZlUserloginlog zlUserloginlog = new ZlUserloginlog();

            //获取 token得到微信用户Id
            Long weiXinUserId = TokenUtil.getUserId(token);
            if (weiXinUserId != null) {
                //根据微信用户id查询
                ZlWxuser zlUser = new ZlWxuser();
                zlUser.setUserid(weiXinUserId);
                ZlWxuser zlWxuser = zlWxuserMapper.selectOne(zlUser);
                zlUserloginlog.setUserid(weiXinUserId);
                zlUserloginlog.setNickname(zlWxuser.getNickname());
                zlUserloginlog.setLoginip(IPUtils.getIpAddr(request));
                ZlHotel zlHotel = zlHotelMapper.getById(String.valueOf(zlWxuser.getHotelid()));
                zlUserloginlog.setHotelname(zlHotel.getHotelname());
                zlUserloginlog.setHotelid(Integer.valueOf(hotelId));
                zlUserloginlog.setCreatedate(DateUtils.getCurrentTimestamp());
            }
            zlUserloginlog.setRoomid(Integer.valueOf(roomId));
            zlUserloginlogMapper.insert(zlUserloginlog);
        }

        //非空情况,从缓存中获取数据
        Object value = redisTemplate.boundValueOps(RedisConstant.REDIS_HOTEL_KEY + ":" + hotelId).get();
        if (value != null) {
            ZlHotelIn zlHotelInCache = GsonUtils.gsonMaps(value.toString(), ZlHotelIn.class);
            return new ReturnString(zlHotelInCache);
        }
        //从数据库中读取数据
        ZlHotel zlHotel = zlHotelMapper.getById(hotelId);
     
        if (zlHotel != null) {

            //根据酒店ID获取菜单
            zlXcxMenuList = zlXcxMenuMapper.getMenuList(String.valueOf(zlHotel.getHotelid()));

            //根据酒店ID获取轮播图，对应酒店不足情况后补剩余 默认为三张
            List<ZlBanner> zlBanner = zlBannerService.findBanner(Integer.valueOf(hotelId),0);

            //根据酒店Id获取公告
            List<zlNews> zlNews = zlNewsMapper.findAllJiuDianId(zlHotel.getHotelid(), 1, 1);

            ZlHotelIn zlHotelIn = new ZlHotelIn();

            //拷贝元数据
            BeanUtils.copyProperties(zlHotel, zlHotelIn);

            zlHotelIn.setZlBannerList(CollectionUtils.isEmpty(zlBanner) && zlBanner.size() <= 0 ?
                    Lists.newArrayList() : zlBanner);

            zlHotelIn.setZlXcxmenus(CollectionUtils.isEmpty(zlXcxMenuList) && zlXcxMenuList.size() <= 0 ?
                    Lists.newArrayList() : zlXcxMenuList);

            zlHotelIn.setZlNews(zlNews);

            if (zlHotelroom != null) {
                zlHotelIn.setHotelroom(zlHotelroom);
            }

            String hotelJson = GsonUtils.objToJson(zlHotelIn);

            ZlHotelIn sjJiuDianIn1 = GsonUtils.gsonMaps(hotelJson, ZlHotelIn.class);

            //存储数据至缓存中
            redisTemplate.boundValueOps(RedisConstant.REDIS_HOTEL_KEY + ":" + hotelId).set(hotelJson, 100, TimeUnit.SECONDS);
            return new ReturnString(sjJiuDianIn1);
           }
        }
        return new ReturnString("数据加载失败");
    }
}
