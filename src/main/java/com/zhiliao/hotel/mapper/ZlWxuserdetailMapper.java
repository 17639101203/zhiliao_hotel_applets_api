package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlWxuserdetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ZlWxuserdetailMapper extends Mapper<ZlWxuserdetail> {

    ZlWxuserdetail findByUserId(@Param("userId") Long userId);

    void updateWxuserdetail(@Param("zlWxuserdetail") ZlWxuserdetail zlWxuserdetail);
}