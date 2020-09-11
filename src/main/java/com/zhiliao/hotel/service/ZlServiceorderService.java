package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.myAppointment.dto.ZlServiceorderDTO2;
import com.zhiliao.hotel.controller.serviceorder.params.ServiceorderCommitParams;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderCommitVo;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderInfoVo;

import java.util.Map;

public interface ZlServiceorderService {

    /**
     * 客房服务订单提交
     * @param token
     * @param scp
     * @return
     */
    Map<String, Object> serviceorderSubmit(String token, ServiceorderCommitParams scp) throws RuntimeException;

    /**
     * 获取客房服务订单详情
     * @param orderId
     * @return
     */
    ServiceorderInfoVo getServiceorderInfo(Long orderId) throws RuntimeException;

    /**
     * 客房服务订单取消
     * @param orderId
     * @throws RuntimeException
     */
    void serviceorderCancel(Long orderId) throws RuntimeException;

    void userDeleteServiceOrder(Long orderid);
}
