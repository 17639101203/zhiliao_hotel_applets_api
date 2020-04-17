package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 
 *
 * @author null
 * @date 2020-04-15
 */
public class ZlTag implements Serializable {
    /**
     * 标签ID
     */
    private Integer tagid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 标签名称
     */
    private String tagname;

    /**
     * 标签类型:1酒店环境;2房型;3点赞吐槽
     */
    private Short type;

    /**
     * 
     */
    private Integer sort;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * 更新日期
     */
    private Integer updatedate;

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public Integer getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        return "ZlTag{" +
                "tagid=" + tagid +
                ", hotelid=" + hotelid +
                ", tagname='" + tagname + '\'' +
                ", type=" + type +
                ", sort=" + sort +
                ", isdelete=" + isdelete +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                '}';
    }

    public ZlTag(Integer tagid, Integer hotelid, String tagname, Short type, Integer sort, Boolean isdelete, Integer createdate, Integer updatedate) {
        this.tagid = tagid;
        this.hotelid = hotelid;
        this.tagname = tagname;
        this.type = type;
        this.sort = sort;
        this.isdelete = isdelete;
        this.createdate = createdate;
        this.updatedate = updatedate;
    }

    public ZlTag() {
    }
}