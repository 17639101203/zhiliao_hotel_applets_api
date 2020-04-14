package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.model.ZlCart;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * Created by xiegege on 2020/4/14.
 */
public interface ZlCartMapper extends Mapper<ZlCart> {

    void updateCartGoodsCount(@Param("cartId") Long cartId, @Param("goodsCount") Integer goodsCount, @Param("updateDate") Integer updateDate);

    List<UserCartVo> findUserCart(@Param("hotelId") Integer hotelId, @Param("userId") Long userId, @Param("belongModule") Integer belongModule);

    void emptyCart(@Param("hotelId") Integer hotelId, @Param("userId") Long userId, @Param("belongModule") Integer belongModule);
}