package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 
 *
 * @author null
 * @date 2020-04-14
 */
public class ZlComment implements Serializable {
    /**
     * 
     */
    private Integer commentid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 1好评 2中评 3差评
     */
    private Byte evaluation;

    /**
     * 标签id,多个用 | 隔开
     */
    private String tagids;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论类型 0:点赞吐槽
     */
    private Byte type;

    /**
     * 0:未通过;1已通过
     */
    private Byte commentstatus;

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

    /**
     * 酒店名称
     */
    private String hotelname;

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Byte getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Byte evaluation) {
        this.evaluation = evaluation;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getCommentstatus() {
        return commentstatus;
    }

    public void setCommentstatus(Byte commentstatus) {
        this.commentstatus = commentstatus;
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
        return "ZlComment{" +
                "commentid=" + commentid +
                ", hotelid=" + hotelid +
                ", userid=" + userid +
                ", evaluation=" + evaluation +
                ", tagids='" + tagids + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", commentstatus=" + commentstatus +
                ", isdelete=" + isdelete +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", hotelname=" + hotelname +
                '}';
    }

    public ZlComment(Integer commentid, Integer hotelid, Long userid, Byte evaluation, String tagids, String content, Byte type, Byte commentstatus, Boolean isdelete, Integer createdate, Integer updatedate,String hotelname) {
        this.commentid = commentid;
        this.hotelid = hotelid;
        this.userid = userid;
        this.evaluation = evaluation;
        this.tagids = tagids;
        this.content = content;
        this.type = type;
        this.commentstatus = commentstatus;
        this.isdelete = isdelete;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.hotelname = hotelname;
    }

    public ZlComment() {
    }
}