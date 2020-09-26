package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.goods.vo.BusinessHoursVO;
import com.zhiliao.hotel.controller.hotel.ZlHotelController;
import com.zhiliao.hotel.controller.hotel.in.ZlHotelIn;
import com.zhiliao.hotel.controller.hotel.vo.HotelMoneyVO;
import com.zhiliao.hotel.controller.hotel.vo.ZlXcxmenuVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.*;
import com.zhiliao.hotel.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private final ZlHotelMapper zlHotelMapper;

    @Autowired
    private final ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private final ZlXcxMenuService zlXcxMenuService;

    @Autowired
    private final ZlUserloginlogService zlUserloginlogService;

    @Autowired
    private final HttpServletRequest request;

    @Autowired
    private final ZlWxuserService zlWxuserService;

    @Autowired
    private final ZlNewsMapper zlNewsMapper;

    @Autowired
    private final ZlBannerService zlBannerService;

    @Autowired
    private final RedisCommonUtil redisCommonUtil;

    @Autowired
    private final ZlHotelUserHistoryMapper zlHotelUserHistoryMapper;

    @Autowired
    private ZlGoodsService zlGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlHotelDetailMapper zlHotelDetailMapper;

    @Override
    public ReturnString getById(Integer hotelId, String roomId) {
        //固定 小程序渠道 1为C端
        String state = "1";

        //根据酒店id，客房id
        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getById(roomId, hotelId);
        if (StringUtils.equals(state, "1")) {

            ZlHotel byId = zlHotelMapper.getById(hotelId);
            ZlHotelIn zlHotel = new ZlHotelIn(byId);
            if (zlHotel != null) {

                List<ZlBanner> bannerList = null;
                if (redisTemplate.hasKey(RedisKeyConstant.BANNER_KEY + hotelId)) {
                    bannerList = (List<ZlBanner>) redisTemplate.opsForValue().get(RedisKeyConstant.BANNER_KEY + hotelId);
                } else {
                    bannerList = zlBannerService.findBanner(Integer.valueOf(hotelId), 0);
                    //目前先不放缓存
//                    redisTemplate.opsForValue().set(RedisKeyConstant.BANNER_KEY + hotelId, bannerList);
                }

                //获取轮播图数据
                zlHotel.setZlBannerList(bannerList);

                //根据酒店ID获取菜单
                if (!StringUtils.isEmpty(roomId)) {
                    int roomTypeFlag = 0;
                    if (zlHotelroom != null) {
                        roomTypeFlag = zlHotelroom.getRoomTypeFlag();
                    }
                    List<ZlXcxmenu> zlXcxMenuList = zlXcxMenuService.getMenuList(String.valueOf(zlHotel.getHotelID()), roomTypeFlag);
                    for (ZlXcxmenu zlXcxmenu : zlXcxMenuList) {
                        Integer menuid = zlXcxmenu.getMenuid();
                        BusinessHoursVO businessHoursVO = zlGoodsService.getBusinessHours(menuid);
                        zlXcxmenu.setBusinessHoursVO(businessHoursVO);
                    }
                    zlHotel.setZlXcxMenus(zlXcxMenuList);
                }

                //根据酒店Id获取公告
                List<ZlNews> zlNews = zlNewsMapper.getNewsByHotel(String.valueOf(zlHotel.getHotelID()));

                //平台与酒店对应公告去重 默认酒店下公告
                List<ZlNews> zlNewsList = zlNews.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(
                                        Comparator.comparing(ZlNews::getContent))),
                                ArrayList::new
                        )
                );

                //数据过滤type 1&IsDeleted0
                List<ZlNews> zlNewsCollect = zlNewsList.stream().filter(a -> a.getType() == 1 && a.getIsDelete() == 0).collect(
                        Collectors.toList());

                zlHotel.setZlNews(zlNewsCollect);
            }
            //获取客房数据
            zlHotel.setHotelRoom(zlHotelroom);

            ZlHotelIn hotelData = GsonUtils.gsonMaps(GsonUtils.objToJson(zlHotel), ZlHotelIn.class);
            ZlHotel zlHotelById = zlHotelMapper.getById(hotelId);
            if (zlHotelById != null && StringUtils.isNoneBlank(zlHotelById.getReceptionTel())) {
                hotelData.setReceptionTel(zlHotelMapper.getById(hotelId).getReceptionTel());
            }

            HotelMoneyVO hotelMoneyVO = zlHotelMapper.getHotelMoney(hotelId);
            hotelData.setHotelMoneyVO(hotelMoneyVO);

            //添加酒店经纬度信息
            ZlHotelDetail zlHotelDetail = new ZlHotelDetail();
            zlHotelDetail.setHotelid(hotelId);
            zlHotelDetail = zlHotelDetailMapper.selectOne(zlHotelDetail);
            hotelData.setLongitude(zlHotelDetail.getLongitude());//经度
            hotelData.setLatitude(zlHotelDetail.getLatitude());//纬度

            Byte serviceTime = zlHotelMapper.getServiceTime(hotelId);
            if (serviceTime != null) {
                hotelData.setServiceTime(serviceTime);
            }

            return new ReturnString(hotelData);
        }
        return new ReturnString("数据加载失败");
    }

    private void addZlUserLoginLog(Long userId, Integer hotelId, Integer roomId, String roomNumBer) {
        ZlUserloginlog zlUserloginlog = new ZlUserloginlog();
        ZlWxuser zlWxuser = zlWxuserService.findWxuserByUserId(userId);
        if (zlWxuser != null) {
            //用户id
            zlUserloginlog.setUserid(zlWxuser.getUserid());
            //微信用户昵称
            zlUserloginlog.setNickname(zlWxuser.getNickname());
            //根据用户所属酒店Id
            ZlHotel zlHotel = zlHotelMapper.getById(hotelId);
            //酒店名称
            zlUserloginlog.setHotelname(zlHotel.getHotelName());
            //酒店id
            zlUserloginlog.setHotelid(Integer.valueOf(zlHotel.getHotelID()));
            //获取当前登录ip地址
            zlUserloginlog.setLoginip(IPUtils.getIpAddr(request));
            //客房名称
            zlUserloginlog.setRoomnumber(roomNumBer);
            //获取当前时间戳
            zlUserloginlog.setCreatedate(DateUtils.javaToPhpNowDateTime());
            //客房Id
            zlUserloginlog.setRoomid(roomId);
            int count = zlUserloginlogService.insert(zlUserloginlog);
            if (count > 0) {
                return;
            }
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

    @Override
    public String getReceptionTel(Integer hotelID) {
        return zlHotelMapper.getReceptionTel(hotelID);
    }

    @Override
    public ZlHotel getByHotelID(Integer hotelId) {
        return zlHotelMapper.getById(hotelId);
    }

    @Override
    public void userDeleteHotelHistory(Long recId) {
        zlHotelUserHistoryMapper.userDeleteHotelHistory(recId);
    }

}
