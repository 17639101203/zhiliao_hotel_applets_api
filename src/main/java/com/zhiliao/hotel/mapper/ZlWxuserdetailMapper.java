package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlWxuserdetail;
import org.apache.ibatis.annotations.Param;

public interface ZlWxuserdetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_wxuserdetail
     *
     * @mbg.generated Mon May 25 11:36:29 CST 2020
     */
    int insert(ZlWxuserdetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_wxuserdetail
     *
     * @mbg.generated Mon May 25 11:36:29 CST 2020
     */
    int insertSelective(ZlWxuserdetail record);

    ZlWxuserdetail findByUserId(@Param("userId") Long userId);
}