package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepairorder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface ZlRepairorderMapper extends Mapper<ZlRepairorder> {

    /**
     * 报修下单
     *
     * @param repairorder
     * @return
     */
    Integer insertRepairorder(ZlRepairorder repairorder);

    /**
     * 查询订单详情
     *
     * @param
     * @return
     */
    Map<String, Object> queryRepairMsg(@Param("userid") Long userid, @Param("serialnumber") String serialnumber);


    /**
     * 取消报修预约
     */
    void removeRepairOrder(
            @Param("serialnumber") String serialnumber, @Param("updatedate") Integer updatedate);

    @Select("select count(*) from zl_repairorder where UserID = #{userId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountRepair(Long userId);

    void userDeleteRepairOrder(@Param("serialnumber") String serialnumber, @Param("updatedate") Integer updateDate);
}
