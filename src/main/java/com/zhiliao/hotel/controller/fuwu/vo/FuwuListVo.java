package com.zhiliao.hotel.controller.fuwu.vo;

/**
 * Created by xiegege on 2020/4/11.
 */

public class FuwuListVo {

    /**
     * 客服服务id
     */
    private Integer id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 售价
     */
    private Float price;
    /**
     * 市场价
     */
    private Float shiChangPrice;
    /**
     * 平台价
     */
    private Float paiTaiPrice;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 库存量
     */
    private Integer count;
    /**
     * 已售数量
     */
    private Integer sales;
    /**
     * 类别名称
     */
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getShiChangPrice() {
        return shiChangPrice;
    }

    public void setShiChangPrice(Float shiChangPrice) {
        this.shiChangPrice = shiChangPrice;
    }

    public Float getPaiTaiPrice() {
        return paiTaiPrice;
    }

    public void setPaiTaiPrice(Float paiTaiPrice) {
        this.paiTaiPrice = paiTaiPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
