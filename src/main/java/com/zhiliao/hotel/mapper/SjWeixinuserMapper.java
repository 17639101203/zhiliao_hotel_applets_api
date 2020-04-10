package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.SjWeixinuser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by xiegege on 2019/10/14.
 */

public interface SjWeixinuserMapper extends Mapper<SjWeixinuser> {

    SjWeixinuser findWeixinuserInfo(@Param("weixinOpenid") String weixinOpenid);
}