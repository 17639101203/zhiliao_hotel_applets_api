package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlWxuser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by xiegege on 2019/10/14.
 */

public interface ZlWxuserMapper extends Mapper<ZlWxuser> {

    void updateWxuser(@Param("wxuser") ZlWxuser wxuser);
}