package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlBanner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 邓菡晨
 * @date 2020/4/14 10:37
 */
public interface ZlBannerMapper extends Mapper<ZlBanner> {

    /**
     * 查询所属有效的轮播图
     */
    List<ZlBanner> findBanner(@Param("hotelID") Integer hotelID, @Param("menuID") Integer menuID);

    /**
     * 查询平台方的轮播图
     */
    List<ZlBanner> findByPlaBanner(@Param("page") Integer page);
}
