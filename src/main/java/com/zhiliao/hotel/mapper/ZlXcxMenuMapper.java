package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlXcxmenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * 酒店小程序菜单数据层
 *
 * @author chenrong
 * @created date 2020/4/14
 */
public interface ZlXcxMenuMapper extends Mapper<ZlXcxmenu> {


    /**
     * 根据酒店Id获取菜单数据
     *
     * @param hotelId
     * @return
     */
    List<ZlXcxmenu> getMenuList(@Param("hotelId") String hotelId);

    ZlXcxmenu getBusinessHours(@Param("menuId") Integer menuId);
}
