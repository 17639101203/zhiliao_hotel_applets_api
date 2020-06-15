package com.zhiliao.hotel.controller.goods.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-19 12:50
 **/
@Data
@Document(indexName = "zlgj", type = "shop")
public class EsGoods {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private Integer goodsid;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart")
    private String goodsname;
    @Field(type = FieldType.Text, store = true)
    private String coverimgurl;
    @Field(type = FieldType.Double, store = true)
    private Double originalprice;
    @Field(type = FieldType.Double, store = true)
    private Double currentprice;
    @Field(type = FieldType.Integer, store = true)
    private Integer goodscategoryid;
    @Field(type = FieldType.Integer, store = true)
    private Integer belongmodule;
    @Field(type = FieldType.Integer, store = true)
    private Integer hotelid;
}
