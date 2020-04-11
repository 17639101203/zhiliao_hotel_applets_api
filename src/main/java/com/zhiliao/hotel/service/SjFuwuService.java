package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.fuwu.vo.FuwuListVo;

import java.util.List;

/**
 * Created by xiegege on 2020/4/11.
 */

public interface SjFuwuService {

    List<FuwuListVo> findFuwuList(Integer jiuDianId);

    FuwuListVo findFuwuDetail(Integer fuwuId);
}
