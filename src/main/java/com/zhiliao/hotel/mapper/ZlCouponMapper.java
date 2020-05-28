package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myOrder.vo.CouponUserVO;
import com.zhiliao.hotel.model.ZlCoupon;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 15:34
 **/
public interface ZlCouponMapper extends Mapper<ZlCoupon> {

    CouponUserVO findByRecID(@Param("recID") Integer recID);

    /**
     * 查询在
     * @param date
     * @return
     */
    List<ZlCoupon> findAll(@Param("date") Integer date);

    void updateById(@Param("couponid") Integer couponid, @Param("date") Integer date);
}
