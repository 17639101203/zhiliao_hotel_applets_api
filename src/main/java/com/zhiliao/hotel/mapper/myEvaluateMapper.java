package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface myEvaluateMapper extends Mapper<ZlComment> {
    List<ZlComment> listEvaluates(@Param("userId") Long userId);

    ZlComment evaluateDetail(@Param("commentid") Integer commentid);
}
