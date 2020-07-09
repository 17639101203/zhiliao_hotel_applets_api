package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.Repair.vo.RepairOrderToPhpVO;
import com.zhiliao.hotel.controller.Repair.vo.RepairOrderVO;
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
    RepairOrderVO queryRepairMsg(@Param("orderID") Long orderID);


    /**
     * 取消报修预约
     */
    void removeRepairOrder(@Param("orderID") Long orderID, @Param("updatedate") Integer updatedate);

    @Select("select count(*) from zl_repairorder where UserID = #{userId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountRepair(Long userId);

    void userDeleteRepairOrder(@Param("orderID") Long orderID, @Param("updatedate") Integer updateDate);

    void updateCommentImg(@Param("orderid") Long orderid, @Param("imageurls") String imageurls);

    RepairOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}
