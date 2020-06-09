package com.zhiliao.hotel.service;

/**
 * 菜单点击记录
 *
 * @author 邓菡晨
 * @date 2020/4/15 8:57
 */
public interface ZlMenuvisitlogService {

    void add(Integer menuId, Long userId);
}
