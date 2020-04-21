package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlXcxmenucustom;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 酒店小程序菜单数据层
 * @author  chenrong
 * @created date 2020/4/14
 */
public interface ZlXcxMenuCustomMapper extends Mapper<ZlXcxmenucustom> {

    List<ZlXcxmenucustom> getMenuList(@Param("hotelId") String hotelId);

}
