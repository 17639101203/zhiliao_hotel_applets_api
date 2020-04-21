package com.zhiliao.hotel.service.impl;


import com.google.common.collect.Lists;

import com.google.common.reflect.TypeToken;
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
import javax.swing.*;
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
        List<ZlBanner> zlBanners = null;
        //固定 小程序渠道 1为C端
        String state = "1";
        if (state.equals("1")) {
            if (!StringUtils.isEmpty(roomId)) {
                zlHotelroom = zlHotelRoomMapper.getById(roomId, hotelId);
                ZlUserloginlog zlUserloginlog = new ZlUserloginlog();

                //获取 token得到微信用户Id
                Long weiXinUserId = TokenUtil.getUserId(token);
                if (weiXinUserId != null) {
                    ZlWxuser zlUser = new ZlWxuser();
                    zlUser.setUserid(weiXinUserId);
                    //根据微信用户id查询
                    ZlWxuser zlWxuser = zlWxuserMapper.selectOne(zlUser);
                    zlUserloginlog.setUserid(weiXinUserId);
                    zlUserloginlog.setNickname(zlWxuser.getNickname());
                    zlUserloginlog.setLoginip(IPUtils.getIpAddr(request));

                    //根据用户所属酒店Id
                    ZlHotel zlHotel = zlHotelMapper.getById(String.valueOf(zlWxuser.getHotelid()));
                    zlUserloginlog.setHotelname(zlHotel.getHotelname());
                    zlUserloginlog.setHotelid(Integer.valueOf(hotelId));
                    zlUserloginlog.setCreatedate(DateUtils.getCurrentTimestamp());
                }
                zlUserloginlog.setRoomid(Integer.valueOf(roomId));
                //客房扫描率录入
                zlUserloginlogMapper.insert(zlUserloginlog);
            }

            ZlHotel zlHotel = zlHotelMapper.getById(hotelId);
            if (zlHotel != null) {
                //根据酒店ID获取菜单
                zlXcxMenuList = zlXcxMenuMapper.getMenuList(String.valueOf(zlHotel.getHotelid()));

                ZlHotelIn zlHotelIn = new ZlHotelIn();

                String bannerValue = (String) redisTemplate.boundValueOps(RedisConstant.BANNER_KEY + ":" + hotelId).get();
                //非空直接读取缓存
                if (!StringUtils.isEmpty(bannerValue)) {
                    zlBanners = GsonUtils.jsonToList(bannerValue, ZlBanner.class);
                } else {
                    //根据酒店ID获取轮播图，对应酒店不足情况后补剩余 默认为三张
                    zlBanners = zlBannerService.findBanner(Integer.valueOf(hotelId), 0);
                    redisTemplate.boundValueOps(RedisConstant.BANNER_KEY + ":" + hotelId).set(GsonUtils.objToJson(zlBanners));
                }
                //获取轮播图数据
                zlHotelIn.setZlBannerList(zlBanners);

                //根据酒店Id获取公告
                List<zlNews> zlNews = zlNewsMapper.findAllJiuDianId(zlHotel.getHotelid(), 1, 1);

                //拷贝元数据
                BeanUtils.copyProperties(zlHotel, zlHotelIn);

                //获取菜单数据
                zlHotelIn.setZlXcxmenus(CollectionUtils.isEmpty(zlXcxMenuList) && zlXcxMenuList.size() <= 0 ?
                        Lists.newArrayList() : zlXcxMenuList);

                //获取多条公告数据
                zlHotelIn.setZlNews(zlNews);

                //客房Id非空情况则获取客房数据
                if (zlHotelroom != null) {
                    zlHotelIn.setHotelroom(zlHotelroom);
                }

                String hotelJson = GsonUtils.objToJson(zlHotelIn);

                ZlHotelIn hotelData = GsonUtils.gsonMaps(hotelJson, ZlHotelIn.class);

                return new ReturnString(hotelData);
            }
        }
        return new ReturnString("数据加载失败");
    }
}
