package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisConstant;
import com.zhiliao.hotel.controller.hotel.in.ZlHotelIn;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlBannerService;
import com.zhiliao.hotel.service.ZlHotelService;
import com.zhiliao.hotel.service.ZlUserloginlogService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.GsonUtils;
import com.zhiliao.hotel.utils.IPUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
public class ZlHotelServiceImpl implements ZlHotelService {

    private final ZlHotelMapper zlHotelMapper;

    private final RedisTemplate redisTemplate;

    private final ZlHotelRoomMapper zlHotelRoomMapper;

    private final ZlXcxMenuMapper zlXcxMenuMapper;

    private final ZlUserloginlogService zlUserloginlogService;

    private final HttpServletRequest request;

    private final ZlWxuserMapper zlWxuserMapper;

    private final ZlNewsMapper zlNewsMapper;

    private final ZlBannerService zlBannerService;

    @Autowired
    public ZlHotelServiceImpl(ZlHotelMapper zlHotelMapper, RedisTemplate redisTemplate, ZlHotelRoomMapper zlHotelRoomMapper, ZlXcxMenuMapper zlXcxMenuMapper,
                              ZlUserloginlogService zlUserloginlogService, HttpServletRequest request, ZlWxuserMapper zlWxuserMapper, ZlNewsMapper zlNewsMapper, ZlBannerService zlBannerService) {
        this.zlHotelMapper = zlHotelMapper;
        this.redisTemplate = redisTemplate;
        this.zlHotelRoomMapper = zlHotelRoomMapper;
        this.zlXcxMenuMapper = zlXcxMenuMapper;
        this.zlUserloginlogService = zlUserloginlogService;
        this.request = request;
        this.zlWxuserMapper = zlWxuserMapper;
        this.zlNewsMapper = zlNewsMapper;
        this.zlBannerService = zlBannerService;
    }

    @Override
    public ReturnString getById(String hotelId, String roomId, String token) throws ParseException {
        ZlHotelroom zlHotelroom = null;
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
                    zlUserloginlog.setRoomnumber("房间名测试cr123");
                    //根据用户所属酒店Id
                    ZlHotel zlHotel = zlHotelMapper.getById(String.valueOf(zlWxuser.getHotelid()));
                    zlUserloginlog.setHotelname(zlHotel.getHotelname());
                    zlUserloginlog.setHotelid(Integer.valueOf(hotelId));
                    zlUserloginlog.setCreatedate(Long.valueOf(DateUtils.javaToPhpNowDateTime()));
                }
                zlUserloginlog.setRoomid(Integer.valueOf(roomId));
                //客房扫描率录入
                zlUserloginlogService.insert(zlUserloginlog);
            }

            ZlHotel zlHotel = zlHotelMapper.getById(hotelId);
            if (zlHotel != null) {
                ZlHotelIn zlHotelIn = new ZlHotelIn();

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
                zlHotelIn.setZlBannerList(zlBanners1);

                //拷贝元数据
                BeanUtils.copyProperties(zlHotel, zlHotelIn);

                //根据酒店ID获取菜单
                List<ZlXcxmenu> zlXcxMenuList = zlXcxMenuMapper.getMenuList(String.valueOf(zlHotel.getHotelid()));
                zlHotelIn.setZlXcxmenus(zlXcxMenuList);

                //根据酒店Id获取公告
                List<ZlNews> zlNews = zlNewsMapper.findAllJiuDianId(zlHotel.getHotelid(), 1, 1);
                zlHotelIn.setZlNews(zlNews);

                //获取客房数据
                zlHotelIn.setHotelroom(zlHotelroom);

                String hotelJson = GsonUtils.objToJson(zlHotelIn);

                ZlHotelIn hotelData = GsonUtils.gsonMaps(hotelJson, ZlHotelIn.class);

                return new ReturnString(hotelData);
            }
        }
        return new ReturnString("数据加载失败");
    }
}
