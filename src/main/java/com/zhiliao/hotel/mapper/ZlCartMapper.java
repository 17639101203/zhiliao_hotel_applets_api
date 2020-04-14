package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCart;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


/**
 * Created by xiegege on 2020/4/14.
 */
public interface ZlCartMapper extends Mapper<ZlCart> {

    void updateCartGoodsCount(@Param("cartId") Long cartId, @Param("goodsCount") Integer goodsCount, @Param("updateDate") Integer updateDate);
}