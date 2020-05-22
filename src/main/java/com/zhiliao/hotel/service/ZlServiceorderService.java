package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.serviceorder.params.ServiceorderCommitParams;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderCommitVo;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderInfoVo;

public interface ZlServiceorderService {

    /**
     * 客房服务订单提交
     * @param token
     * @param scp
     * @return
     */
    ServiceorderCommitVo serviceorderSubmit(String token, ServiceorderCommitParams scp) throws RuntimeException;

    /**
     * 获取客房服务订单详情
     * @param orderId
     * @return
     */
    ServiceorderInfoVo getServiceorderInfo(Long orderId)  throws RuntimeException;
}
