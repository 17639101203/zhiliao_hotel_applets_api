package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepairorder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ZlRepairorderMyMapper extends Mapper<ZlRepairorder> {

    List<Map<String,Object>> findAllByUserId(@Param("userId") Long userId, @Param("orderstatus") Integer orderstatus);


}
