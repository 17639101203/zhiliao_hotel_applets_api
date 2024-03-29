package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myAppointment.dto.ZlServiceorderDTO2;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderToPhpVO;
import com.zhiliao.hotel.model.ZlServiceorder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ZlServiceorderMapper extends Mapper<ZlServiceorder> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_serviceorder
     *
     * @mbg.generated Wed May 20 17:41:35 CST 2020
     */
//    int insert(ZlServiceorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_serviceorder
     *
     * @mbg.generated Wed May 20 17:41:35 CST 2020
     */
//    int insertSelective(ZlServiceorder record);

    ZlServiceorderDTO2 getByOrderIdDetil(@Param("orderId") Long orderId);

    ZlServiceorder getByOrderId(@Param("orderId") Long orderId);

    int updateOrderStatusById(@Param("orderId") Long orderId, @Param("updateDate") Integer updateDate);

    @Select("select count(*) from zl_serviceorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountService(@Param("userId") Long userId, @Param("hotelId") Integer hotelId);

    void userDeleteServiceOrder(@Param("orderid") Long orderid, @Param("updateDate") Integer updateDate);

    ServiceorderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}