package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepairorder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface ZlRepairorderMapper extends Mapper<ZlRepairorder> {

    /**
     * 报修下单
     * @param repairorder
     * @return
     */
    Integer insertRepairorder(ZlRepairorder repairorder);

    /**
     * 查询订单详情
     * @param
     * @return
     */
    Map<String,Object> queryRepairMsg(@Param("userid") Long userid,@Param("serialnumber") String serialnumber);


    /**
     * 取消报修预约
     */
    void removeRepairOrder(@Param("userid") Long userid,
                           @Param("serialnumber") String serialnumber,@Param("updatedate") Integer updatedate);


}
