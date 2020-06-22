package com.zhiliao.hotel.controller.file.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-22 09:10
 **/
@Data
public class UploadPhotoParam {

    @ApiModelProperty(value = "特殊标记", required = true)
    String specialName;

}
