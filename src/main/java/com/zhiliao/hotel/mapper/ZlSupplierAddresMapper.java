package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlSupplierAddress;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-07 17:24
 **/
public interface ZlSupplierAddresMapper extends Mapper<ZlSupplierAddress> {
    ZlSupplierAddress getSupplierAddress(@Param("orderId") Long orderId);
}
