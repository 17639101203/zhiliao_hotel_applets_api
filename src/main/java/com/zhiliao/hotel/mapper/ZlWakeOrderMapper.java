package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlWakeOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-05 15:48
 **/
public interface ZlWakeOrderMapper extends Mapper<ZlWakeOrder> {
    ZlWakeOrder wakeOrderDetail(@Param("orderID") Long orderID);
}
