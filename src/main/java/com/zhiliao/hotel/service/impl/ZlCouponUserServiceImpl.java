package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.coupon.result.ZlCouponUserResult;
import com.zhiliao.hotel.mapper.ZlCouponUserMapper;
import com.zhiliao.hotel.service.ZlCouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCouponUserServiceImpl implements ZlCouponUserService {


    @Autowired
    private ZlCouponUserMapper couponUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 获取我的优惠卷
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult listCouponUser(Long userId, Integer pageNo, Integer pageSize) {

        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        System.out.println(date);

        //查询有效优惠卷
        List<ZlCouponUserResult> effective = couponUserMapper.effectiveCouponUser(userId, date);
        //已使用的优惠卷
        List<ZlCouponUserResult> used = couponUserMapper.usedCouponUser(userId);
        //已过期
        List<ZlCouponUserResult> beOverdue = couponUserMapper.beOverdue(userId, date);
        //总集合
        PageHelper.startPage(pageNo, pageSize);
        List<ZlCouponUserResult> couponUserList = new LinkedList<>();
        for (ZlCouponUserResult zlCouponUser : effective) {
            couponUserList.add(zlCouponUser);
        }
        for (ZlCouponUserResult zlCouponUser : used) {
            couponUserList.add(zlCouponUser);
        }
        for (ZlCouponUserResult zlCouponUser : beOverdue) {
            couponUserList.add(zlCouponUser);
        }
        //集合分页
        List<ZlCouponUserResult> list = new LinkedList<>();

        int pageNoMax = 0;
        if (couponUserList.size() % pageSize == 0) {
            pageNoMax = couponUserList.size() / pageSize;
        } else {
            pageNoMax = couponUserList.size() / pageSize + 1;
        }


        int startPageNo = pageNo < pageNoMax ? pageNo : pageNoMax;
        int startIndex = (startPageNo - 1) * pageSize;
//        int dx = (pageNo - 1) * pageSize + pageSize > couponUserList.size() ? couponUserList.size() : (pageNo - 1) * pageSize + pageSize;
        int endIndex = (startPageNo - 1) * pageSize + pageSize < couponUserList.size() ? (startPageNo - 1) * pageSize + pageSize : couponUserList.size();

        for (int i = startIndex; i < endIndex; i++) {
            ZlCouponUserResult zlCouponUser = couponUserList.get(i);
            list.add(zlCouponUser);
        }

        PageInfoResult<ZlCouponUserResult> result = new PageInfoResult<>();
        result.setPageNo(pageNo);
        result.setCurrentPageNumber(list.size());
        result.setTotalItem(couponUserList.size());
        if (couponUserList.size() % pageSize == 0) {
            result.setTotalPages(couponUserList.size() / pageSize);
        } else {
            result.setTotalPages(couponUserList.size() / pageSize + 1);
        }
        result.setList(list);
        return result;
    }

    /**
     * 获取有效优惠卷数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer count(Long userId) {
        List<ZlCouponUserResult> effective = getZlCouponUsers(userId);
        return effective.size();
    }

    /**
     * 可用优惠卷
     *
     * @param userId
     * @return
     */
    @Override
    public PageInfoResult effective(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        //当前时间
        List<ZlCouponUserResult> effective = getZlCouponUsers(userId);
        PageInfo<ZlCouponUserResult> pageInfo = new PageInfo<>(effective);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 方法的封装
     *
     * @param userId
     * @return
     */
    private List<ZlCouponUserResult> getZlCouponUsers(Long userId) {
        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        //查询有效优惠卷
        List<ZlCouponUserResult> effective = couponUserMapper.effectiveCouponUser(userId, date);
        if (effective != null) {
            for (int i = 0; i < effective.size(); i++) {
                Integer recid = effective.get(i).getRecid();
                Boolean boo = redisTemplate.hasKey(RedisKeyConstant.ORDER_RECID + recid);
                if (boo) {
                    effective.remove(i);
                }
            }
            return effective;
        }
        return null;
    }

    /*private List<ZlCouponUser> getCouponUsers(Long userId) {
        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        List<ZlCouponUser> list = couponUserMapper.list(userId);
        Collections.sort(list, new Comparator<ZlCouponUser>() {
            @Override
            public int compare(ZlCouponUser o1, ZlCouponUser o2) {
                //有效优惠卷
                if (o1.getIsused() == false && o2.getIsused() == false && o1.getExpireddate() < date && o2.getExpireddate() <date){
                    return 0;
                }
                return 0;
            }
        });
        return list;
    }*/
}
