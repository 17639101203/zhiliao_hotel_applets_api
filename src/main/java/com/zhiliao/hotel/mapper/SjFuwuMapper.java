package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.fuwu.vo.FuwuListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xiegege on 2020/4/11.
 */
@Mapper
public interface SjFuwuMapper {

    List<FuwuListVo> findFuwuList(@Param("jiuDianId") Integer jiuDianId);

    FuwuListVo findFuwuDetail(@Param("fuwuId") Integer fuwuId);
}