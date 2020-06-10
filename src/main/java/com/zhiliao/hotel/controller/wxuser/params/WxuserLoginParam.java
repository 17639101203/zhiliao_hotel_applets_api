package com.zhiliao.hotel.controller.wxuser.params;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-08 09:48
 **/
@Data
public class WxuserLoginParam {

    private String code;

    private String encryptedData;

    private String iv;


}
