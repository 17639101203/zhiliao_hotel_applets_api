package com.zhiliao.hotel.controller.comment.commentparm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("我的评价列表返回值")
public class CommentVO {

    /**
     * 自增ID
     */
    @ApiModelProperty("自增ID")
    private Integer commentid;

    /**
     * 1好评 2中评 3差评
     */
    @ApiModelProperty("1好评 2中评 3差评")
    private Byte evaluation;

    /**
     * 标签id,多个用 | 隔开
     */
    @ApiModelProperty("标签id,多个用 | 隔开")
    private String tagids;

    /**
     * 评论图片地址（多张）
     */
    @ApiModelProperty("评论图片地址（多张）")
    private String imageurls;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;

    /**
     * 酒店名
     */
    @ApiModelProperty("酒店名")
    private String hotelname;

    /**
     * 标签名
     */
    @ApiModelProperty("标签名")
    private List<String> tagname;
}