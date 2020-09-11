package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.coupon.dto.CouponIdListDTO;
import com.zhiliao.hotel.controller.coupon.dto.CouponReceivedDTO;
import com.zhiliao.hotel.controller.coupon.vo.CouponReceivedVO;
import com.zhiliao.hotel.controller.coupon.vo.ZlCouponAllVO;
import com.zhiliao.hotel.controller.coupon.vo.ZlCouponInfoVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlCouponService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 09:58
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCouponServiceImpl implements ZlCouponService {

    private static final Logger logger = LoggerFactory.getLogger(ZlCouponServiceImpl.class);

    @Autowired
    private ZlCouponMapper couponMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlCouponuserMapper zlCouponuserMapper;

    @Autowired
    private ZlCouponhotelMapper zlCouponhotelMapper;

    @Autowired
    private ZlCouponmoduleMapper zlCouponmoduleMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private ZlCouponruleMapper zlCouponruleMapper;

    @Override
    public Map<String, List<ZlCoupon>> couponUnclaimed(Long userId) {

        //先查出有效优惠券
        List<ZlCouponAllVO> zlCouponAllVOList = couponMapper.couponUnclaimed();

        if (CollectionUtils.isEmpty(zlCouponAllVOList)) {
            throw new BizException(0, "当前系统没有发放优惠券...");
        }

        //定义符合条件的优惠券id集合
//        List<Integer> couponIdList = new LinkedList<>();
        List<Integer> couponruleIdList = new LinkedList<>();

        //筛选出对该用户有效的优惠券返回给前端
        for (ZlCouponAllVO zlCouponAllVO : zlCouponAllVOList) {
            Integer couponId = zlCouponAllVO.getId();//优惠券id
            Integer putnum = zlCouponAllVO.getPutnum();//优惠券投放数量(0:不限量 N：N张)
            Byte gainnum = zlCouponAllVO.getGainnum();//领取次数限制
            Integer couponruleid = zlCouponAllVO.getCouponruleid();//优惠券规则id
            Byte suitusergroup = zlCouponAllVO.getSuitusergroup();//适用领取人(0：默认全部注册用户 1:指定用户群体 2:指定个别用户)
            //当前时间
            int currentTime = Math.toIntExact(System.currentTimeMillis() / 1000);
            if (zlCouponAllVO.getEffecttype() == 0) {
                //优惠券有效期限类型为"区间时间"类型
                if (currentTime >= zlCouponAllVO.getStarttime() && currentTime <= zlCouponAllVO.getEndtime()) {
                    //当前时间在时间内
                    if (putnum == 0) {
                        //优惠券投放数量为无限量
//                        Integer count = couponMapper.getReceiveCouponCount2(couponId, userId);
                        Integer count = couponMapper.getReceiveCouponCount2(couponruleid, userId);
                        if (gainnum > count) {
                            //该用户领取的次数小于该优惠券领取次数限制
                            if (suitusergroup == 0) {
//                                couponIdList.add(couponId);
                                couponruleIdList.add(couponruleid);
                            } else if (suitusergroup == 1) {
                                List<Long> list = couponMapper.getUserGroup(couponruleid);
                                for (Long userIdGroup : list) {
                                    if (userIdGroup.equals(userId)) {
//                                        couponIdList.add(couponId);
                                        couponruleIdList.add(couponruleid);
                                    }
                                }
                            } else if (suitusergroup == 2) {
                                List<Long> list = couponMapper.getUserList(couponruleid);
                                for (Long userIdGroup : list) {
                                    if (userIdGroup.equals(userId)) {
//                                        couponIdList.add(couponId);
                                        couponruleIdList.add(couponruleid);
                                    }
                                }
                            }
                        }
                    } else {
                        //优惠券投放数量指定数量
                        Integer putReceiveNum = couponMapper.getReceiveCouponCount(couponruleid);
                        if (putReceiveNum < putnum) {
                            //该优惠券被领取数量小于优惠券设置的可领取数量
                            Integer gainReceiveNum = couponMapper.getReceiveCouponCount2(couponruleid, userId);
                            if (gainReceiveNum < gainnum) {
                                //该用户领取的次数小于该优惠券领取次数限制
                                if (suitusergroup == 0) {
//                                    couponIdList.add(couponId);
                                    couponruleIdList.add(couponruleid);
                                } else if (suitusergroup == 1) {
                                    List<Long> list = couponMapper.getUserGroup(couponruleid);
                                    for (Long userIdGroup : list) {
                                        if (userIdGroup.equals(userId)) {
//                                            couponIdList.add(couponId);
                                            couponruleIdList.add(couponruleid);
                                        }
                                    }
                                } else if (suitusergroup == 2) {
                                    List<Long> list = couponMapper.getUserList(couponruleid);
                                    for (Long userIdGroup : list) {
                                        if (userIdGroup.equals(userId)) {
//                                            couponIdList.add(couponId);
                                            couponruleIdList.add(couponruleid);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (zlCouponAllVO.getEffecttype() == 1) {
                //优惠券有效期限类型为"自领取日起N天"类型
                if (currentTime >= zlCouponAllVO.getGainstarttime() && currentTime <= zlCouponAllVO.getGainendtime()) {
                    //当前时间在时间内
                    if (putnum == 0) {
                        //优惠券投放数量为无限量
                        Integer count = couponMapper.getReceiveCouponCount2(couponruleid, userId);
                        if (gainnum > count) {
                            //该用户领取的次数小于该优惠券领取次数限制
                            if (suitusergroup == 0) {
//                                couponIdList.add(couponId);
                                couponruleIdList.add(couponruleid);
                            } else if (suitusergroup == 1) {
                                List<Long> list = couponMapper.getUserGroup(couponruleid);
                                for (Long userIdGroup : list) {
                                    if (userIdGroup.equals(userId)) {
//                                        couponIdList.add(couponId);
                                        couponruleIdList.add(couponruleid);
                                    }
                                }
                            } else if (suitusergroup == 2) {
                                List<Long> list = couponMapper.getUserList(couponruleid);
                                for (Long userIdGroup : list) {
                                    if (userIdGroup.equals(userId)) {
//                                        couponIdList.add(couponId);
                                        couponruleIdList.add(couponruleid);
                                    }
                                }
                            }
                        }
                    } else {
                        //优惠券投放数量指定数量
                        Integer putReceiveNum = couponMapper.getReceiveCouponCount(couponruleid);
                        if (putReceiveNum < putnum) {
                            //该优惠券被领取数量小于优惠券设置的可领取数量
                            Integer gainReceiveNum = couponMapper.getReceiveCouponCount2(couponruleid, userId);
                            if (gainReceiveNum < gainnum) {
                                //该用户领取的次数小于该优惠券领取次数限制
                                if (suitusergroup == 0) {
//                                    couponIdList.add(couponId);
                                    couponruleIdList.add(couponruleid);
                                } else if (suitusergroup == 1) {
                                    List<Long> list = couponMapper.getUserGroup(couponruleid);
                                    for (Long userIdGroup : list) {
                                        if (userIdGroup.equals(userId)) {
//                                            couponIdList.add(couponId);
                                            couponruleIdList.add(couponruleid);
                                        }
                                    }
                                } else if (suitusergroup == 2) {
                                    List<Long> list = couponMapper.getUserList(couponruleid);
                                    for (Long userIdGroup : list) {
                                        if (userIdGroup.equals(userId)) {
//                                            couponIdList.add(couponId);
                                            couponruleIdList.add(couponruleid);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(couponruleIdList)) {
            throw new BizException(0, "当前系统没有发放优惠券...");
        }

        Map<String, List<ZlCoupon>> zlCouponMap = new HashMap<>();
        List<ZlCoupon> zlCouponList1 = new LinkedList<>();
        List<ZlCoupon> zlCouponList2 = new LinkedList<>();
        List<ZlCoupon> zlCouponList3 = new LinkedList<>();

        for (Integer integer : couponruleIdList) {
            ZlCouponrule zlCouponrule = new ZlCouponrule();
            zlCouponrule.setId(integer);
            zlCouponrule = zlCouponruleMapper.selectOne(zlCouponrule);
            ZlCoupon zlCoupon = new ZlCoupon();
            zlCoupon.setId(zlCouponrule.getCouponid());
            zlCoupon = couponMapper.selectOne(zlCoupon);
            zlCoupon.setRuletitle(zlCouponrule.getTitle());
            zlCoupon.setCouponruleid(integer);
            if (zlCoupon.getType() == 1) {
                zlCouponList1.add(zlCoupon);
            } else if (zlCoupon.getType() == 2) {
                zlCouponList2.add(zlCoupon);
            } else if (zlCoupon.getType() == 3) {
                zlCouponList3.add(zlCoupon);
            }
        }

        if (!CollectionUtils.isEmpty(zlCouponList1)) {
            zlCouponMap.put("MMQ", zlCouponList1);
        }
        if (!CollectionUtils.isEmpty(zlCouponList2)) {
            zlCouponMap.put("ZKQ", zlCouponList2);
        }
        if (!CollectionUtils.isEmpty(zlCouponList3)) {
            zlCouponMap.put("DJQ", zlCouponList3);
        }

        return zlCouponMap;
    }

    @Override
    public void couponReceive(Long userId, List<CouponIdListDTO> couponIdListDTOList) {

        for (CouponIdListDTO couponIdListDTO : couponIdListDTOList) {
            Integer couponId = couponIdListDTO.getCouponId();
            Integer couponruleId = couponIdListDTO.getCouponruleId();
            //生成券码
            String couponNo = OrderIDUtil.createOrderID("");
            int currentTime = Math.toIntExact(System.currentTimeMillis() / 1000);

//            ZlCouponInfoVO zlCouponInfoVO = couponMapper.getZlCouponInfo(couponId);
            ZlCouponInfoVO zlCouponInfoVO = couponMapper.getZlCouponInfo(couponId, couponruleId);

            if (zlCouponInfoVO == null) {
                throw new BizException("参数有误!");
            }

            ZlCouponuser zlCouponuser = new ZlCouponuser();

            zlCouponuser.setCouponid(zlCouponInfoVO.getCouponid());
            zlCouponuser.setCouponruleid(zlCouponInfoVO.getCouponruleid());
            zlCouponuser.setUserid(userId);

            ZlWxuser zlWxuser = new ZlWxuser();
            zlWxuser.setUserid(userId);
            zlWxuser = zlWxuserMapper.selectOne(zlWxuser);

            zlCouponuser.setNickname(zlWxuser.getNickname());
            zlCouponuser.setCouponno(couponNo);
            zlCouponuser.setCoupontype(zlCouponInfoVO.getType());
            zlCouponuser.setComeformid((byte) 1);
            zlCouponuser.setStatus((byte) 0);
            zlCouponuser.setCreatedate(currentTime);
            zlCouponuser.setUpdatedate(currentTime);

            if (zlCouponInfoVO.getEffecttype() == 0) {
                zlCouponuser.setStarttime(zlCouponInfoVO.getStarttime());
                zlCouponuser.setEndtime(zlCouponInfoVO.getEndtime());
                zlCouponuserMapper.insertSelective(zlCouponuser);
                int timeDifference = zlCouponInfoVO.getEndtime() - zlCouponInfoVO.getStarttime();
                redisTemplate.opsForValue().set(RedisKeyConstant.COUPON_USER_FLAG + zlCouponuser.getId(), zlCouponuser, timeDifference, TimeUnit.SECONDS);
            } else if (zlCouponInfoVO.getEffecttype() == 1) {
                Integer days = zlCouponInfoVO.getDays();
                int endtime = Math.toIntExact(System.currentTimeMillis() / 1000 + days * 24 * 60 * 60);
                zlCouponuser.setStarttime(currentTime);
                zlCouponuser.setEndtime(endtime);
                zlCouponuserMapper.insertSelective(zlCouponuser);
                int timeDifference = endtime - currentTime;
                redisTemplate.opsForValue().set(RedisKeyConstant.COUPON_USER_FLAG + zlCouponuser.getId(), zlCouponuser, timeDifference, TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void autoCouponUserStatus(String couponUserId) {
        zlCouponuserMapper.updateCouponUserStatus(couponUserId);
    }

    @Override
    public Map<String, List<CouponReceivedVO>> couponReceived(Long userId, CouponReceivedDTO couponReceivedDTO) {

        if (null == couponReceivedDTO.getHotelid()) {
            throw new BizException("酒店id不能为空...");
        }
        Integer hotelid = couponReceivedDTO.getHotelid();//获取酒店id

        if (null == couponReceivedDTO.getIsforshop()) {
            throw new BizException("是否购物标记isforshop不能为空...");
        }
        if (couponReceivedDTO.getIsforshop()) {
            if (couponReceivedDTO.getBelongmodule() == null) {
                throw new BizException("模块id为空,请重新填写...");
            }
            if (couponReceivedDTO.getBelongmodule() != 1
                    && couponReceivedDTO.getBelongmodule() != 2
                    && couponReceivedDTO.getBelongmodule() != 3
                    && couponReceivedDTO.getBelongmodule() != 4) {
                throw new BizException("模块id有误,请重新填写...");
            }
        }

        //查询该用户的所有优惠券
        List<CouponReceivedVO> couponReceivedVOList = couponMapper.getCouponReceivedAll(userId);

        if (CollectionUtils.isEmpty(couponReceivedVOList)) {
            return null;
        }

        //去除掉在redis中被锁定的优惠券
        Iterator<CouponReceivedVO> couponReceivedVOIterator = couponReceivedVOList.iterator();
        while (couponReceivedVOIterator.hasNext()) {
            CouponReceivedVO couponReceivedVO = couponReceivedVOIterator.next();
            Long couponuserid = couponReceivedVO.getCouponuserid();
            if (redisTemplate.hasKey(RedisKeyConstant.ORDER_COUPONUSERID + couponuserid)) {
                couponReceivedVOIterator.remove();
            }
        }

        //定义map集合来装优惠券集合
        Map<String, List<CouponReceivedVO>> map = new HashMap<>();
        //定义有效的优惠券集合
        List<CouponReceivedVO> couponEffectiveList = new LinkedList<>();
        //定义有效但是不能使用的优惠券集合
        List<CouponReceivedVO> couponEffectiveNotUseList = new LinkedList<>();
        //定义已使用的优惠券集合
        List<CouponReceivedVO> couponUserdList = new LinkedList<>();
        //定义已过期的优惠券
        List<CouponReceivedVO> couponExpiredList = new LinkedList<>();

        if (couponReceivedDTO.getIsforshop()) {
            for (CouponReceivedVO couponReceivedVO : couponReceivedVOList) {
                //是否全部酒店
                Byte suithotel = couponReceivedVO.getSuithotel();
                //是否全部模块
                Byte suitgoodscategray = couponReceivedVO.getSuitgoodscategray();
                //优惠券信息
                Byte status = couponReceivedVO.getStatus();
                //优惠券id
                Integer couponid = couponReceivedVO.getCouponid();
                //优惠券规则id
                Integer couponruleid = couponReceivedVO.getCouponruleid();

                //所有酒店和所有模块都可以使用的理想情况
                if (suithotel == 0 && suitgoodscategray == 0) {
                    if (status == -1) {
                        couponExpiredList.add(couponReceivedVO);
                    } else if (status == 0) {
                        couponEffectiveList.add(couponReceivedVO);
                    } else if (status == 1) {
                        couponUserdList.add(couponReceivedVO);
                    }
                }

                //指定酒店且所有模块通用
                if (suithotel == 1 && suitgoodscategray == 0) {
                    ZlCouponhotel zlCouponhotel = new ZlCouponhotel();
                    zlCouponhotel.setCouponruleid(couponruleid);
                    zlCouponhotel.setHotelid(hotelid);
                    ZlCouponhotel couponhotel = zlCouponhotelMapper.selectOne(zlCouponhotel);
                    if (couponhotel != null) {
                        if (status == -1) {
                            couponExpiredList.add(couponReceivedVO);
                        } else if (status == 0) {
                            couponEffectiveList.add(couponReceivedVO);
                        } else if (status == 1) {
                            couponUserdList.add(couponReceivedVO);
                        }
                    }
                }

                //指定模块且所有酒店通用
                if (suithotel == 0 && suitgoodscategray == 1) {
                    ZlCouponmodule zlCouponmodule = new ZlCouponmodule();
                    zlCouponmodule.setCouponruleid(couponruleid);
                    List<ZlCouponmodule> zlCouponmoduleList = zlCouponmoduleMapper.select(zlCouponmodule);
                    List<Integer> belongmoduleList = new LinkedList<>();
                    if (!CollectionUtils.isEmpty(zlCouponmoduleList)) {
                        for (ZlCouponmodule couponmodule : zlCouponmoduleList) {
                            Integer moduleid = couponmodule.getModuleid();
                            belongmoduleList.add(moduleid);
                        }
                        for (ZlCouponmodule couponmodule : zlCouponmoduleList) {
                            Integer moduleid = couponmodule.getModuleid();
                            if (moduleid.equals(couponReceivedDTO.getBelongmodule())) {
                                if (status == -1) {
                                    couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                    couponExpiredList.add(couponReceivedVO);
                                    break;
                                } else if (status == 0) {
                                    couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                    couponEffectiveList.add(couponReceivedVO);
                                    break;
                                } else if (status == 1) {
                                    couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                    couponUserdList.add(couponReceivedVO);
                                    break;
                                }
                            }
                        }
                    }
                }

                //指定酒店且指定模块
                if (suithotel == 1 && suitgoodscategray == 1) {
                    ZlCouponhotel zlCouponhotel = new ZlCouponhotel();
                    zlCouponhotel.setCouponruleid(couponruleid);
                    zlCouponhotel.setHotelid(hotelid);
                    ZlCouponhotel couponhotel = zlCouponhotelMapper.selectOne(zlCouponhotel);
                    if (couponhotel != null) {
                        ZlCouponmodule zlCouponmodule = new ZlCouponmodule();
                        zlCouponmodule.setCouponruleid(couponruleid);

                        if (couponReceivedDTO.getBelongmodule() == null) {
                            throw new BizException("模块id为空,请重新填写...");
                        }

                        List<ZlCouponmodule> zlCouponmoduleList = zlCouponmoduleMapper.select(zlCouponmodule);
                        List<Integer> belongmoduleList = new LinkedList<>();
                        if (!CollectionUtils.isEmpty(zlCouponmoduleList)) {
                            for (ZlCouponmodule couponmodule : zlCouponmoduleList) {
                                Integer moduleid = couponmodule.getModuleid();
                                belongmoduleList.add(moduleid);
                            }
                            for (ZlCouponmodule couponmodule : zlCouponmoduleList) {
                                Integer moduleid = couponmodule.getModuleid();
                                if (moduleid.equals(couponReceivedDTO.getBelongmodule())) {
                                    if (status == -1) {
                                        couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                        couponExpiredList.add(couponReceivedVO);
                                        break;
                                    } else if (status == 0) {
                                        couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                        couponEffectiveList.add(couponReceivedVO);
                                        break;
                                    } else if (status == 1) {
                                        couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                        couponUserdList.add(couponReceivedVO);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (CouponReceivedVO couponReceivedVO : couponReceivedVOList) {
                //是否全部酒店
                Byte suithotel = couponReceivedVO.getSuithotel();
                //是否全部模块
                Byte suitgoodscategray = couponReceivedVO.getSuitgoodscategray();
                //优惠券信息
                Byte status = couponReceivedVO.getStatus();
                //优惠券id
                Integer couponid = couponReceivedVO.getCouponid();
                //优惠券规则id
                Integer couponruleid = couponReceivedVO.getCouponruleid();

/*                //所有酒店可以使用的理想情况
                if (suithotel == 0) {
                    if (status == -1) {
                        couponExpiredList.add(couponReceivedVO);
                    } else if (status == 0) {
                        couponEffectiveList.add(couponReceivedVO);
                    } else if (status == 1) {
                        couponUserdList.add(couponReceivedVO);
                    }
                }

                //指定酒店可以使用
                if (suithotel == 1) {
                    ZlCouponhotel zlCouponhotel = new ZlCouponhotel();
                    zlCouponhotel.setCouponruleid(couponruleid);
                    zlCouponhotel.setHotelid(hotelid);
                    ZlCouponhotel couponhotel = zlCouponhotelMapper.selectOne(zlCouponhotel);
                    if (couponhotel != null) {
                        if (status == -1) {
                            couponExpiredList.add(couponReceivedVO);
                        } else if (status == 0) {
                            couponEffectiveList.add(couponReceivedVO);
                        } else if (status == 1) {
                            couponUserdList.add(couponReceivedVO);
                        }
                    }
                }*/

                //所有酒店和所有模块都可以使用的理想情况
                if (suithotel == 0 && suitgoodscategray == 0) {
                    if (status == -1) {
                        couponExpiredList.add(couponReceivedVO);
                    } else if (status == 0) {
                        couponEffectiveList.add(couponReceivedVO);
                    } else if (status == 1) {
                        couponUserdList.add(couponReceivedVO);
                    }
                }

                //指定酒店且所有模块通用
                if (suithotel == 1 && suitgoodscategray == 0) {
                    ZlCouponhotel zlCouponhotel = new ZlCouponhotel();
                    zlCouponhotel.setCouponruleid(couponruleid);
                    zlCouponhotel.setHotelid(hotelid);
                    ZlCouponhotel couponhotel = zlCouponhotelMapper.selectOne(zlCouponhotel);
                    if (couponhotel != null) {
                        if (status == -1) {
                            couponExpiredList.add(couponReceivedVO);
                        } else if (status == 0) {
                            couponEffectiveList.add(couponReceivedVO);
                        } else if (status == 1) {
                            couponUserdList.add(couponReceivedVO);
                        }
                    }
                }

                //指定模块且所有酒店通用
                if (suithotel == 0 && suitgoodscategray == 1) {
                    ZlCouponmodule zlCouponmodule = new ZlCouponmodule();
                    zlCouponmodule.setCouponruleid(couponruleid);
                    List<ZlCouponmodule> zlCouponmoduleList = zlCouponmoduleMapper.select(zlCouponmodule);
                    List<Integer> belongmoduleList = new LinkedList<>();
                    if (!CollectionUtils.isEmpty(zlCouponmoduleList)) {
                        for (ZlCouponmodule couponmodule : zlCouponmoduleList) {
                            Integer moduleid = couponmodule.getModuleid();
                            belongmoduleList.add(moduleid);
                        }
                        if (status == -1) {
                            couponReceivedVO.setBelongmoduleList(belongmoduleList);
                            couponExpiredList.add(couponReceivedVO);
                        } else if (status == 0) {
                            couponReceivedVO.setBelongmoduleList(belongmoduleList);
                            couponEffectiveList.add(couponReceivedVO);
                        } else if (status == 1) {
                            couponReceivedVO.setBelongmoduleList(belongmoduleList);
                            couponUserdList.add(couponReceivedVO);
                        }
                    }
                }

                //指定酒店且指定模块
                if (suithotel == 1 && suitgoodscategray == 1) {
                    ZlCouponhotel zlCouponhotel = new ZlCouponhotel();
                    zlCouponhotel.setCouponruleid(couponruleid);
                    zlCouponhotel.setHotelid(hotelid);
                    ZlCouponhotel couponhotel = zlCouponhotelMapper.selectOne(zlCouponhotel);
                    if (couponhotel != null) {
                        ZlCouponmodule zlCouponmodule = new ZlCouponmodule();
                        zlCouponmodule.setCouponruleid(couponruleid);

                        List<ZlCouponmodule> zlCouponmoduleList = zlCouponmoduleMapper.select(zlCouponmodule);
                        List<Integer> belongmoduleList = new LinkedList<>();
                        if (!CollectionUtils.isEmpty(zlCouponmoduleList)) {
                            for (ZlCouponmodule couponmodule : zlCouponmoduleList) {
                                Integer moduleid = couponmodule.getModuleid();
                                belongmoduleList.add(moduleid);
                            }
                            if (status == -1) {
                                couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                couponExpiredList.add(couponReceivedVO);
                            } else if (status == 0) {
                                couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                couponEffectiveList.add(couponReceivedVO);
                            } else if (status == 1) {
                                couponReceivedVO.setBelongmoduleList(belongmoduleList);
                                couponUserdList.add(couponReceivedVO);
                            }
                        }
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(couponEffectiveList)) {
            map.put("YX", couponEffectiveList);
        }
        if (!CollectionUtils.isEmpty(couponUserdList)) {
            map.put("YSY", couponUserdList);
        }
        if (!CollectionUtils.isEmpty(couponExpiredList)) {
            map.put("YGQ", couponExpiredList);
        }

        return map;
    }

}
