package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.model.ZlCart;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * Created by xiegege on 2020/4/14.
 */
public interface ZlCartMapper extends Mapper<ZlCart> {

    List<UserCartVo> findUserCart(@Param("hotelId") Integer hotelId, @Param("userId") Long userId);

    void deleteUserCart(@Param("hotelId") Integer hotelId, @Param("userId") Long userId);

    void addUserCartBatch(@Param("hotelId") Integer hotelId, @Param("userId") Long userId, @Param("addCartParams") List<AddCartParam> addCartParams, @Param("date") Integer date);
}