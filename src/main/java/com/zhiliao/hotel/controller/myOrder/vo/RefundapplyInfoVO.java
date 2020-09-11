package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-15 14:23
 **/
@Data
public class RefundapplyInfoVO {

    /**
     * 退款申请信息状态参数(1表示可以直接调用退款接口,2表示需要审核)
     */
    private Integer refundapplyInfoStatus;

    /**
     * 退款申请信息内容
     */
    private String content;

}
