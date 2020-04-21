package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepair;
import org.apache.ibatis.annotations.Param;

public interface ZlRepairMapper {

    /**
     * 插入报修订单信息
     * @param zlrepair
     * @return
     */
    public Integer insertzlrepair(ZlRepair zlrepair);


    /**
     * 插入图片路径到报修
     * @param imgurls
     * @param userid
     * @return
     */
    public Integer insertzlrepairImgUrls(@Param("imgurls") String imgurls, @Param("userid")Long userid, @Param("createdate")Integer createdate);
}
