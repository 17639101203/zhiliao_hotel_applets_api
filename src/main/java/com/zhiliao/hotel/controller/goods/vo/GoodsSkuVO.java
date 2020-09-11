package com.zhiliao.hotel.controller.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-04 13:49
 **/
@Data
public class GoodsSkuVO {

    @ApiModelProperty(value = "规格名称")
    private String propertyName;

    @ApiModelProperty(value = "规格图片")
    private String imgUrl;

    @ApiModelProperty(value = "规格ID")
    private Integer propertyId;

    @ApiModelProperty(value = "属性分类ID")
    private Integer categoryId;

}
