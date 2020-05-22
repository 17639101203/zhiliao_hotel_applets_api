package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.hotel.in.ZlHotelIn;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlBannerService;
import com.zhiliao.hotel.service.ZlHotelService;
import com.zhiliao.hotel.service.ZlUserloginlogService;
import com.zhiliao.hotel.service.ZlWxuserService;
import com.zhiliao.hotel.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * 酒店业务实现类
 *
 * @author chenrong
 * @created date 2020/4/10
 */
@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class ZlHotelServiceImpl implements ZlHotelService {

    private final ZlHotelMapper zlHotelMapper;

    private final ZlHotelRoomMapper zlHotelRoomMapper;

    private final ZlXcxMenuMapper zlXcxMenuMapper;

    private final ZlUserloginlogService zlUserloginlogService;

    private final HttpServletRequest request;

    private final ZlWxuserService zlWxuserService;

    private final ZlNewsMapper zlNewsMapper;

    private final ZlBannerService zlBannerService;

    private final RedisCommonUtil redisCommonUtil;

    private final ZlHotelUserHistoryMapper zlHotelUserHistoryMapper;

    @Override
    public ReturnString getById(String hotelId, String roomId, String token) {
        ZlHotelroom zlHotelroom = null;
        //固定 小程序渠道 1为C端
        String state = "1";
        if (state.equals("1")) {
            if (!StringUtils.isEmpty(roomId)) {
                //根据酒店id，客房id
                zlHotelroom = zlHotelRoomMapper.getById(roomId, hotelId);

                //判断房间是否被绑定
                if(zlHotelroom.getRoomstatus()==1){
                     return new ReturnString("该房号已被绑定");
                }

                if (!StringUtils.isEmpty(token)) {
                    //获取 token得到微信用户Id
                    Long weiXinUserId = TokenUtil.getUserId(token);
                    if (weiXinUserId != null) {
                        //客房状态改变为已被绑定
                        zlHotelRoomMapper.updateById(roomId);

                        //客房扫描率录入
                        addZlUserLoginLog(weiXinUserId, Integer.valueOf(roomId));
                    }
                }
            }

            ZlHotelIn zlHotel = new ZlHotelIn(zlHotelMapper.getById(hotelId));
            if (zlHotel != null) {
                //获取缓存value
                String bannerValue = (String) redisCommonUtil.getCache(RedisKeyConstant.BANNER_KEY + ":" + hotelId);

                //判断缓存中是否有数据，没数据直接往数据库查
                List<ZlBanner> zlBanners = Optional.ofNullable(bannerValue).map((val) -> GsonUtils.jsonToList(bannerValue, ZlBanner.class)).
                        orElse(zlBannerService.findBanner(Integer.valueOf(hotelId), 0));

                //判断缓存没数据情况则添加
                if (!Optional.ofNullable(bannerValue).isPresent()) {
                    redisCommonUtil.setCache(RedisKeyConstant.BANNER_KEY + ":" + hotelId, GsonUtils.objToJson(zlBanners));
                }
                //获取轮播图数据
                zlHotel.setZlBannerList(zlBanners);

                //根据酒店ID获取菜单
                List<ZlXcxmenu> zlXcxMenuList = zlXcxMenuMapper.getMenuList(String.valueOf(zlHotel.getHotelID()));
                zlHotel.setZlXcxMenus(zlXcxMenuList);

                //根据酒店Id获取公告
                List<ZlNews> zlNews = zlNewsMapper.findAllJiuDianId(zlHotel.getHotelID(), 1, 1);
                zlHotel.setZlNews(zlNews);

                //获取客房数据
                zlHotel.setHotelRoom(zlHotelroom);

                ZlHotelIn hotelData = GsonUtils.gsonMaps(GsonUtils.objToJson(zlHotel), ZlHotelIn.class);

                return new ReturnString(hotelData);
            }
        }
        return new ReturnString("数据加载失败");
    }

    private void addZlUserLoginLog(Long userId, Integer roomId) {
        ZlUserloginlog zlUserloginlog = new ZlUserloginlog();
        ZlWxuser zlWxuser = zlWxuserService.findWxuserByUserId(userId);
        //用户id
        zlUserloginlog.setUserid(zlWxuser.getUserid());
        //微信用户昵称
        zlUserloginlog.setNickname(zlWxuser.getNickname());
        //获取当前登录ip地址
        zlUserloginlog.setLoginip(IPUtils.getIpAddr(request));
        //客房名称
        zlUserloginlog.setRoomnumber("房间名测试cr123");
        //根据用户所属酒店Id
        ZlHotel zlHotel = zlHotelMapper.getById(String.valueOf(zlWxuser.getHotelid()));
        //酒店名称
        zlUserloginlog.setHotelname(zlHotel.getHotelName());
        //酒店id
        zlUserloginlog.setHotelid(Integer.valueOf(zlHotel.getHotelID()));
        //获取当前时间戳
        zlUserloginlog.setCreatedate(Long.valueOf(DateUtils.javaToPhpNowDateTime()));
        //客房Id
        zlUserloginlog.setRoomid(roomId);
        int count = zlUserloginlogService.insert(zlUserloginlog);
        if (count > 0) {
            return;
        }
    }

    @Override
    public PageInfoResult getHotelHistoryList(String token, Integer pageNo, Integer pageSize) {
        //获取 token得到微信用户Id
        Long userId = TokenUtil.getUserId(token);
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        //获取用户酒店入住历史
        List<ZlHotelUserHistory> hotelHistoryList = zlHotelUserHistoryMapper.getHotelHistoryList(userId);
        PageInfo<ZlHotelUserHistory> pageInfo = new PageInfo<>(hotelHistoryList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }
}