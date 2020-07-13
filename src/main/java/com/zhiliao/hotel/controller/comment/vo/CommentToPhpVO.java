package com.zhiliao.hotel.controller.comment.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-13 17:15
 **/
@Data
public class CommentToPhpVO {

    private Integer CommentID;//自增ID



    private Integer HotelID;//酒店ID
    private Integer RoomID;//
    private Long UserID;//用户ID
    private String UserName;//用户名
    private String Tel;//用户联系电话
    private Byte Evaluation;//1好评 2中评 3差评
    private String TagIDs;//标签id,多个用 | 隔开
    private String ImageUrls;//评论图片地址（多张）用“|”隔开
    private String Content;//评论内容
    private Byte Type;//评论类型 0:点赞吐槽
    private Byte CommentStatus;//0:未通过;1已通过
    private String RoomNumber;//房间号
    private String ReplyContent;//回复内容
    private String OperatorName;//操作人员
    private Integer ReplyDate;//回复时间
    private Byte ReplyReadStatus;//0:未读；1已读
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Integer CreateDate;//添加日期
    private Integer UpdateDate;//更新日期
    
}
