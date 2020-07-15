package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.clean.vo.CleanOrderToPhpVO;
import com.zhiliao.hotel.model.ZlCleanOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;


public interface ZlCleanOrderMapper extends Mapper<ZlCleanOrder> {

    Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    Map<String, Object> selectCleanDetails(@Param("orderID") Long orderID);

    void removeCleanOrder(@Param("orderID") Long orderID, @Param("updatedate") Integer updatedate);

    @Select("select count(*) from zl_cleanorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 ")
    int selectCountClean(@Param("userId")Long userId, @Param("hotelId")Integer hotelId);

    void deleteCleanOrder(@Param("orderID") Long orderID, @Param("nowTime") Integer nowTime);

    CleanOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}