package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlTag implements Serializable {
    /**
     * 标签ID
     */
    private Integer tagid;

    /**
     * 酒店ID;0平台
     */
    private Integer hotelid;



    /**
     * 标签ID
     */
    private String tagname;

    /**
     * 标签类型:1酒店环境;2房型;3点赞吐槽
     */
    private Short type;

    /**
     * 排序
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


}