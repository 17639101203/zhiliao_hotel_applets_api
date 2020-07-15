package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.hotellive.vo.ZlCheckoutOrderToPhpVO;
import com.zhiliao.hotel.model.ZlCheckoutOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:06
 **/
public interface ZlCheckoutOrderMapper extends Mapper<ZlCheckoutOrder> {
    void cancelCheckoutOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    void userDeleteCheckoutOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    @Select("select count(*) from zl_checkoutorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountCheckOut(@Param("userId")Long userId, @Param("hotelId")Integer hotelId);

    ZlCheckoutOrder checkoutOrderDetail(@Param("orderID") Long orderID);

    ZlCheckoutOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}
