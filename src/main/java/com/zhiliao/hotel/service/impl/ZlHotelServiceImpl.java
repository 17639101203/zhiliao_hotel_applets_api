package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisConstant;
import com.zhiliao.hotel.controller.hotel.in.ZlHotelIn;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlBannerService;
import com.zhiliao.hotel.service.ZlHotelService;
import com.zhiliao.hotel.service.ZlUserloginlogService;
import com.zhiliao.hotel.service.ZlWxuserService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.GsonUtils;
import com.zhiliao.hotel.utils.IPUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * 酒店业务实现类
 * @author chenrong
 * @created date 2020/4/10
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelServiceImpl implements ZlHotelService {

    private final ZlHotelMapper zlHotelMapper;

    private final RedisTemplate redisTemplate;

    private final ZlHotelRoomMapper zlHotelRoomMapper;

    private final ZlXcxMenuMapper zlXcxMenuMapper;

    private final ZlUserloginlogService zlUserloginlogService;

    private final HttpServletRequest request;

    private final ZlWxuserService zlWxuserService;

    private final ZlNewsMapper zlNewsMapper;

    private final ZlBannerService zlBannerService;

    @Autowired
    public ZlHotelServiceImpl(ZlHotelMapper zlHotelMapper, RedisTemplate redisTemplate, ZlHotelRoomMapper zlHotelRoomMapper, ZlXcxMenuMapper zlXcxMenuMapper,
                              ZlUserloginlogService zlUserloginlogService, HttpServletRequest request, ZlWxuserService zlWxuserService, ZlNewsMapper zlNewsMapper, ZlBannerService zlBannerService) {
        this.zlHotelMapper = zlHotelMapper;
        this.redisTemplate = redisTemplate;
        this.zlHotelRoomMapper = zlHotelRoomMapper;
        this.zlXcxMenuMapper = zlXcxMenuMapper;
        this.zlUserloginlogService = zlUserloginlogService;
        this.request = request;
        this.zlWxuserService = zlWxuserService;
        this.zlNewsMapper = zlNewsMapper;
        this.zlBannerService = zlBannerService;
    }

    @Override
    public ReturnString getById(String hotelId, String roomId, String token) {
        ZlHotelroom zlHotelroom = null;
        //固定 小程序渠道 1为C端
        String state = "1";
        if (state.equals("1")) {
            if (!StringUtils.isEmpty(roomId)) {
                //根据酒店id，客房id
                zlHotelroom = zlHotelRoomMapper.getById(roomId, hotelId);
                ZlUserloginlog zlUserloginlog = new ZlUserloginlog();
                //获取 token得到微信用户Id
                Long weiXinUserId = TokenUtil.getUserId(token);
                if (weiXinUserId != null) {
                    //根据微信用户id查询
                    ZlWxuser zlWxuser = zlWxuserService.findWxuserByUserId(weiXinUserId);
                    //用户id
                    zlUserloginlog.setUserid(weiXinUserId);
                    //微信用户昵称
                    zlUserloginlog.setNickname(zlWxuser.getNickname());
                    //获取当前登录ip地址
                    zlUserloginlog.setLoginip(IPUtils.getIpAddr(request));
                    //客房名称
                    zlUserloginlog.setRoomnumber("房间名测试cr123");
                    //根据用户所属酒店Id
                    ZlHotel zlHotel = zlHotelMapper.getById(String.valueOf(zlWxuser.getHotelid()));
                    //酒店名称
                    zlUserloginlog.setHotelname(zlHotel.getHotelname());
                    //酒店id
                    zlUserloginlog.setHotelid(Integer.valueOf(hotelId));
                    //获取当前时间戳
                    zlUserloginlog.setCreatedate(Long.valueOf(DateUtils.javaToPhpNowDateTime()));
                    //客房Id
                    zlUserloginlog.setRoomid(Integer.valueOf(roomId));
                }
                //客房扫描率录入
                zlUserloginlogService.insert(zlUserloginlog);
            }

            ZlHotelIn zlHotel = new ZlHotelIn(zlHotelMapper.getById(hotelId));
            if (zlHotel != null) {
                //获取缓存value
                String bannerValue = (String) redisTemplate.boundValueOps(RedisConstant.BANNER_KEY + ":" + hotelId).get();

                //判断缓存中是否有数据，没数据直接往数据库查
                List<ZlBanner> zlBanners1 = Optional.ofNullable(bannerValue).map((val) -> GsonUtils.jsonToList(bannerValue, ZlBanner.class)).
                        orElse(zlBannerService.findBanner(Integer.valueOf(hotelId), 0));

                //判断缓存没数据情况则添加
                if (!Optional.ofNullable(bannerValue).isPresent()) {
                    redisTemplate.boundValueOps(RedisConstant.BANNER_KEY + ":" + hotelId).set(GsonUtils.objToJson(zlBanners1));
                }
                //获取轮播图数据
                zlHotel.setZlBannerList(zlBanners1);

                //根据酒店ID获取菜单
                List<ZlXcxmenu> zlXcxMenuList = zlXcxMenuMapper.getMenuList(String.valueOf(zlHotel.getHotelid()));
                zlHotel.setZlXcxmenus(zlXcxMenuList);

                //根据酒店Id获取公告
                List<ZlNews> zlNews = zlNewsMapper.findAllJiuDianId(zlHotel.getHotelid(), 1, 1);
                zlHotel.setZlNews(zlNews);

                //获取客房数据
                zlHotel.setHotelroom(zlHotelroom);

                ZlHotelIn hotelData = GsonUtils.gsonMaps(GsonUtils.objToJson(zlHotel), ZlHotelIn.class);

                return new ReturnString(hotelData);
            }
        }
        return new ReturnString("数据加载失败");
    }
}
