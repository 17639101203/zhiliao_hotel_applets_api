package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myAppointment.result.ZlServiceorderResult;
import com.zhiliao.hotel.model.ZlServiceorder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlServiceorderMyMapper extends Mapper<ZlServiceorder> {

    List<ZlServiceorderResult> serviceFindAll(@Param("userId") Long userId, @Param("orderstatus") Integer orderstatus);
}