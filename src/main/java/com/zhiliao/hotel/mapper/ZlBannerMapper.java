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

    //查询所属有效的轮播图
    @Select("SELECT * FROM zl_banner WHERE  HotelID = #{hotelID} and MenuID = #{menuID} and  BannerStatus = 1 and IsDelete = 0  LIMIT 5")
    List<ZlBanner> findBanner(@Param("hotelID") Integer hotelID, @Param("menuID") Integer menuID);

    /**
     * 查询平台方的轮播图
     */
    @Select("select * from zl_banner where hotelID = 0 order by Sort DESC limit 0, #{page}")
    List<ZlBanner> findByPlaBanner(Integer page);
}
