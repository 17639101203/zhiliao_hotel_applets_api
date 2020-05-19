package com.zhiliao.hotel.controller.goods.vo;

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
@Document(indexName = "zlgjxcx", type = "goods")
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

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getCoverimgurl() {
        return coverimgurl;
    }

    public void setCoverimgurl(String coverimgurl) {
        this.coverimgurl = coverimgurl;
    }

    public Double getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(Double originalprice) {
        this.originalprice = originalprice;
    }

    public Double getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(Double currentprice) {
        this.currentprice = currentprice;
    }

    public Integer getGoodscategoryid() {
        return goodscategoryid;
    }

    public void setGoodscategoryid(Integer goodscategoryid) {
        this.goodscategoryid = goodscategoryid;
    }

    public Integer getBelongmodule() {
        return belongmodule;
    }

    public void setBelongmodule(Integer belongmodule) {
        this.belongmodule = belongmodule;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    @Override
    public String toString() {
        return "EsGoods{" +
                "goodsid=" + goodsid +
                ", goodsname='" + goodsname + '\'' +
                ", coverimgurl='" + coverimgurl + '\'' +
                ", originalprice=" + originalprice +
                ", currentprice=" + currentprice +
                ", goodscategoryid=" + goodscategoryid +
                ", belongmodule=" + belongmodule +
                ", hotelid=" + hotelid +
                '}';
    }
}
