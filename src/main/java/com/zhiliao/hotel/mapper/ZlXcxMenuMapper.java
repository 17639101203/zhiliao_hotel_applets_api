package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlXcxmenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 酒店小程序菜单数据层
 * @author  chenrong
 * @created date 2020/4/14
 */
public interface ZlXcxMenuMapper extends Mapper<ZlXcxmenu> {

    List<ZlXcxmenu> getZlXcxMenuList(@Param("menuId") String menuId);

    List<ZlXcxmenu> getMenuList(@Param("hotelId") String hotelId);
}
