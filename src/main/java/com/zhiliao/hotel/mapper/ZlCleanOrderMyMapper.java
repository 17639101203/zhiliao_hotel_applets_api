package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCleanOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2019/10/14.
 */

public interface ZlCleanOrderMyMapper extends Mapper<ZlCleanOrder> {


    List<Map<String,Object>> findAllByStatus(@Param("userId") Long userId, @Param("orderstatus") Integer orderstatus);

}