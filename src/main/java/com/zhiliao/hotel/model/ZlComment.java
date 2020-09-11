package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ZlComment implements Serializable {

    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer commentid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     *
     */
    private Integer roomid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     *
     */
    private String username;

    /**
     * 用户联系电话
     */
    private String tel;

    /**
     * 1好评 2中评 3差评
     */
    private Byte evaluation;

    /**
     * 标签id,多个用 | 隔开
     */
    private String tagids;

    /**
     * 评论图片地址（多张）用“|”隔开
     */
    private String imageurls;

    /**
     * 评论类型 0:点赞吐槽
     */
    private Byte type;

    /**
     * 0:未通过;1已通过
     */
    private Byte commentstatus;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 操作人员
     */
    private String operatorname;

    /**
     * 回复时间
     */
    private Integer replydate;

    /**
     * 0:未读；1已读
     */
    private Byte replyreadstatus;

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
     * 评论内容
     */
    private String content;

    /**
     * 回复内容
     */
    private String replycontent;

}