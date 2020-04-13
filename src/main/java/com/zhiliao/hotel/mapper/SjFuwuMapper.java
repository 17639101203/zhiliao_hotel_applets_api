package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.fuwu.vo.FuwuListVo;
import com.zhiliao.hotel.model.SjFuwu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by xiegege on 2020/4/11.
 */

public interface SjFuwuMapper extends Mapper<SjFuwu> {

    List<FuwuListVo> findFuwuList(@Param("jiuDianId") Integer jiuDianId);

    FuwuListVo findFuwuDetail(@Param("fuwuId") Integer fuwuId);
}