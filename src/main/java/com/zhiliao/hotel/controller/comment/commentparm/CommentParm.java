package com.zhiliao.hotel.controller.comment.commentparm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("添加点赞吐槽接口接收参数")
@Data
@ToString
public class CommentParm {

    @NotNull(message = "酒店id不能为空!")
    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelID;

    @NotNull(message = "评价等级不能为空!")
    @ApiModelProperty(value = "评价等级,1好评 2中评 3差评", required = true)
    private Integer evaluation;

    @ApiModelProperty(value = "评价标签ID 多个用丨隔开", required = false)
    private String tagIDs;

    @ApiModelProperty(value = "评价内容", required = false)
    private String content;

    @NotBlank(message = "房间号不能为空!")
    @NotNull(message = "房间号不能为空!")
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;

    @NotNull(message = "客房Id不能为空!")
    @ApiModelProperty(value = "客房Id", required = true)
    private Integer roomid;

    /**
     * 评论图片地址（多张）用“|”隔开
     */
    @ApiModelProperty(value = "评论图片地址", required = false)
    private String imageurls;

}
