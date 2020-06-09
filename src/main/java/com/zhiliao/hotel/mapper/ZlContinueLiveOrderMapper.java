package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlContinueLiveOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:08
 **/
public interface ZlContinueLiveOrderMapper extends Mapper<ZlContinueLiveOrder> {

    void cancelContinueLiveOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    void userDeleteContinueLiveOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    @Select("select count(*) from zl_continueliveorder where UserID = #{userId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountLive(Long userId);
}
