package com.zhiliao.hotel.controller.comment.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-15 15:23
 **/
@Data
public class CommentDetailVO {
    /**
     * 1好评 2中评 3差评
     */
    private Byte evaluation;

    /**
     * 客房编号
     */
    private String roomnumber;

    /**
     * 标签id,多个用 | 隔开
     */
    private String tagids;

    /**
     * 评论图片地址（多张）
     */
    private String imageurls;

    /**
     * 评论图片列表
     */
    private List<String> imageurllist;

    /**
     * 回复时间
     */
    private Integer replydate;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * 更新日期
     */
    private Integer updatedate;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 回复内容
     */
    private String replycontent;

    /**
     * 标签名称
     */
    private List<String> tagname;

    /**
     * 酒店名称
     */
    private String hotelname;

}
