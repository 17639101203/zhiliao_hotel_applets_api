package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlCouponMapper;
import com.zhiliao.hotel.mapper.ZlCouponUserMapper;
import com.zhiliao.hotel.model.ZlCoupon;
import com.zhiliao.hotel.model.ZlCouponUser;
import com.zhiliao.hotel.service.ZlCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-05-27 14:08
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCouponServiceImpl implements ZlCouponService {

    @Autowired
    private ZlCouponMapper couponMapper;
    @Autowired
    private ZlCouponUserMapper couponUserMapper;

    @Override
    public List<ZlCoupon> couponFindAll(Long userId) {
        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        List<ZlCoupon> coupons = couponMapper.findAll(date);
        if (coupons != null && coupons.size() > 0){
            for (int i = 0; i < coupons.size(); i++) {
                ZlCouponUser couponUser = new ZlCouponUser();
                ZlCoupon zlCoupon = coupons.get(i);
                couponUser.setUserid(userId);
                couponUser.setCouponid(zlCoupon.getCouponid());
                couponUser.setIsused(false);
                couponUser.setIsdelete(false);
                couponUser.setCreatedate(date);
                if (zlCoupon.getLimitdatetype() == 0){
                    couponUser.setExpireddate(date + zlCoupon.getValiddaycount() * 60 * 60);
                }else if (zlCoupon.getLimitdatetype() == 1){
                    couponUser.setExpireddate(zlCoupon.getAllowendusedate());
                }
                if (zlCoupon.getCouponcount() != -1){
                    if(zlCoupon.getCouponcount() > 0){
                        couponUserMapper.insert(couponUser);
                        couponMapper.updateById(zlCoupon.getCouponid(),date);
                    }else {
                        new RuntimeException("该优惠卷已派发完毕");
                    }
                }else {
                    couponUserMapper.insert(couponUser);
                }
            }
        }
        return coupons;
    }
}
