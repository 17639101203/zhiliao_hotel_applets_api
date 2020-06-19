package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.Repair.vo.RepairOrderVO;
import com.zhiliao.hotel.model.ZlRepairorder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ZlRepairService {

    /**
     * 新增报修信息
     *
     * @param zlRepairorder 报修信息
     * @return
     * @throws IOException
     */
    void addRepairMsg(ZlRepairorder zlRepairorder);

    Map<String, Object> addRepairMsg(Long userid, RepairParam repairParam, MultipartFile multipartFile);


    /**
     * 查询报修订单详情
     *
     * @param
     * @return
     */
    RepairOrderVO findRepairOrder(Long orderID);


    /**
     * 取消报修预约
     */
    void cancelRepairOrder(Long orderID, Integer updatedate);

    //用户删除报修订单
    void userDeleteRepairOrder(Long orderID);
}
