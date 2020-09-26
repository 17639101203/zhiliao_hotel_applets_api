package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.hotellive.vo.ZlContinueLiveOrderToPhpVO;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlContinueLiveOrderDTO;
import com.zhiliao.hotel.model.ZlContinueLiveOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:08
 **/
public interface ZlContinueLiveOrderMapper extends Mapper<ZlContinueLiveOrder> {

    void cancelContinueLiveOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    void userDeleteContinueLiveOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    @Select("select count(*) from zl_continueliveorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountLive(@Param("userId")Long userId, @Param("hotelId")Integer hotelId);

    ZlContinueLiveOrderDTO continueLiveOrderDetail(@Param("orderID") Long orderID);

    ZlContinueLiveOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}
