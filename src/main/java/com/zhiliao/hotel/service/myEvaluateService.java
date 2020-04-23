package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlComment;

public interface myEvaluateService {
    /**
     * 获取所有评价信息
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult listEvaluates(Long userId, Integer pageNo, Integer pageSize);

    /**
     * 评价订单详情
     * @param commentid
     * @return
     */
    ZlComment evaluateDetail(Integer commentid);
}
