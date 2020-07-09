package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.wake.vo.ZlWakeOrderToPhpVO;
import com.zhiliao.hotel.model.ZlWakeOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-05 15:48
 **/
public interface ZlWakeOrderMapper extends Mapper<ZlWakeOrder> {
    ZlWakeOrder wakeOrderDetail(@Param("orderID") Long orderID);

    int updateById(ZlWakeOrder wakeOrder);

    @Select("select count(*) from zl_wakeorder where UserID = #{userId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountWake(Long userId);

    ZlWakeOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}
