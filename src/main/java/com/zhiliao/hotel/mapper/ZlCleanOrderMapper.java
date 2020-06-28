package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCleanOrder;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


public interface ZlCleanOrderMapper extends Mapper<ZlCleanOrder> {

    Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    Map<String, Object> selectCleanDetails(@Param("orderID") Long orderID);

    void removeCleanOrder(@Param("orderID") Long orderID, @Param("updatedate") Integer updatedate);

    @Select("select count(*) from zl_cleanorder where UserID = #{userId} and IsDelete = 0 ")
    int selectCountClean(Long userId);

    void deleteCleanOrder(@Param("orderID")Long orderID,@Param("nowTime") Integer nowTime);
}