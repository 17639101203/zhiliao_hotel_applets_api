package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.mapper.ZlCouponUserMapper;
import com.zhiliao.hotel.model.ZlCouponUser;
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
    public List<ZlCouponUser> listCouponUser(Long userId, Integer pageNo, Integer pageSize) {

        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        System.out.println(date);

        //查询有效优惠卷
        List<ZlCouponUser> effective = couponUserMapper.effectiveCouponUser(userId, date);
        //已使用的优惠卷
        List<ZlCouponUser> used = couponUserMapper.usedCouponUser(userId);
        //已过期
        List<ZlCouponUser> beOverdue = couponUserMapper.beOverdue(userId, date);
        //总集合
        PageHelper.startPage(pageNo, pageSize);
        List<ZlCouponUser> couponUserList = new LinkedList<>();
        for (ZlCouponUser zlCouponUser : effective) {
            couponUserList.add(zlCouponUser);
        }
        for (ZlCouponUser zlCouponUser : used) {
            couponUserList.add(zlCouponUser);
        }
        for (ZlCouponUser zlCouponUser : beOverdue) {
            couponUserList.add(zlCouponUser);
        }
        for (int i = 0; i < couponUserList.size(); i++) {
            System.out.println(couponUserList.get(i));
        }
        //集合分页
        List<ZlCouponUser> list = new LinkedList<>();

        int index = (pageNo-1)* pageSize  < couponUserList.size()?(pageNo-1)* pageSize:couponUserList.size();
        int dx = (pageNo-1)* pageSize + pageSize > couponUserList.size() ? couponUserList.size():(pageNo-1)* pageSize  + pageSize;
        for (int i = index; i < dx; i++) {
            ZlCouponUser zlCouponUser = couponUserList.get(i);
            list.add(zlCouponUser);
        }

        return list;
    }

    /**
     * 获取有效优惠卷数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer count(Long userId) {
        List<ZlCouponUser> effective = getZlCouponUsers(userId);
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
        List<ZlCouponUser> effective = getZlCouponUsers(userId);
        PageInfo<ZlCouponUser> pageInfo = new PageInfo<>(effective);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 方法的封装
     *
     * @param userId
     * @return
     */
    private List<ZlCouponUser> getZlCouponUsers(Long userId) {
        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        //查询有效优惠卷
        List<ZlCouponUser> effective = couponUserMapper.effectiveCouponUser(userId, date);
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
