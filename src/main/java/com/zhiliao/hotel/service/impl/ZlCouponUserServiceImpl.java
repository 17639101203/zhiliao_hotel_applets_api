package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlCouponUserMapper;
import com.zhiliao.hotel.model.ZlCouponUser;
import com.zhiliao.hotel.service.ZlCouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCouponUserServiceImpl implements ZlCouponUserService {


    @Autowired
    private ZlCouponUserMapper couponUserMapper;

    /*@Autowired
    private RedisTemplate redisTemplate;*/
    /**
     * 获取我的优惠卷
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult listCouponUser(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlCouponUser> couponUserList = couponUserMapper.listCouponUser(userId);

        PageInfo<ZlCouponUser> pageInfo = new PageInfo<>(couponUserList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 获取有效优惠卷数量
     * @param userId
     * @return
     */
    @Override
    public Integer count(Long userId) {
        //获取当前时间
        Integer newDate = Math.toIntExact(new Date().getTime());
        return couponUserMapper.count(userId,newDate);
    }
}
