package com.zhiliao.hotel.controller.comment.commentparm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("添加点赞吐槽接口接收参数")
@Data
@ToString
public class CommentParm {


    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelID;

    @ApiModelProperty(value = "评价等级,1好评 2中评 3差评", required = true)
    private Integer evaluation;

    @ApiModelProperty(value = "评价标签ID 多个用丨隔开", required = true)
    private String tagIDs;

    @ApiModelProperty(value = "评价内容", required = true)
    private String content;
}
