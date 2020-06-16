package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlXcxmenu;

import java.util.List;

/**
 * @author chenrong
 * 小程序菜单接口
 */
public interface ZlXcxMenuService {

    /**
     * 根据menuName做数据去重 默认根据酒店id下的菜单
     */
    public List<ZlXcxmenu> getMenuList(String hotelId);

}
