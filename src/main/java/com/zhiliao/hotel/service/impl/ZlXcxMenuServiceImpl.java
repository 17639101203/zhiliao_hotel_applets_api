package com.zhiliao.hotel.service.impl;

import com.google.common.collect.Lists;
import com.zhiliao.hotel.controller.invoice.ZlInvoiceController;
import com.zhiliao.hotel.mapper.ZlXcxMenuMapper;
import com.zhiliao.hotel.model.ZlXcxmenu;
import com.zhiliao.hotel.service.ZlXcxMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 小程序菜单业务实现类
 *
 * @author chenrong
 * @created date 2020/4/14
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ZlXcxMenuServiceImpl implements ZlXcxMenuService {
    private static final Logger logger = LoggerFactory.getLogger(ZlXcxMenuServiceImpl.class);
    @Autowired
    private ZlXcxMenuMapper zlXcxMenuMapper;

    @Override
    public List<ZlXcxmenu> getMenuList(String hotelId, Integer roomTypeFlag) {
        List<ZlXcxmenu> menuList = null;
        if (roomTypeFlag == 2) {//改成房间前台或 房间类型
            logger.info("进入获取前台码菜单查询：" + hotelId + "|" + roomTypeFlag);
            menuList = zlXcxMenuMapper.getMenuList(hotelId);
        } else {
            logger.info("进入获取所有菜单查询：" + hotelId + "|" + roomTypeFlag);
            menuList = zlXcxMenuMapper.getMenuList2(hotelId);
        }

        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }

        //平台菜单
        ZlXcxmenu zlXcxmenu = new ZlXcxmenu();
        zlXcxmenu.setHotelid(0);
        zlXcxmenu.setParentid(0);
        List<ZlXcxmenu> zlXcxmenuParentList = zlXcxMenuMapper.select(zlXcxmenu);

        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < zlXcxmenuParentList.size(); i++) {
            ZlXcxmenu xcxmenu = zlXcxmenuParentList.get(i);
            Integer menuid = xcxmenu.getMenuid();
            if (xcxmenu.getMenustatus().equals(-1) || xcxmenu.getIsdelete()) {
                Integer noSowMenuId = getNoSowMenuId(menuid, zlXcxmenuParentList);
                if (noSowMenuId == null) {
                    continue;
                }
                list.add(noSowMenuId);
            }
        }

        for (ZlXcxmenu xcxmenu1 : menuList) {
            for (ZlXcxmenu zlXcxmenu2 : menuList) {
                if (xcxmenu1.getMenuid().equals(zlXcxmenu2.getParentid())) {
                    list.add(xcxmenu1.getMenuid());
                }
            }
        }

        Iterator<ZlXcxmenu> zlXcxmenuIterator = menuList.iterator();
        while (zlXcxmenuIterator.hasNext()) {
            ZlXcxmenu next = zlXcxmenuIterator.next();
            for (Integer integer : list) {
                if (integer.equals(next.getMenuid())) {
                    zlXcxmenuIterator.remove();
                }
            }
        }

        return menuList;
    }


    private Integer getNoSowMenuId(Integer parentId, List<ZlXcxmenu> zlXcxmenuParentList) {
        for (ZlXcxmenu zlXcxmenu : zlXcxmenuParentList) {
            if (zlXcxmenu.getParentid().equals(parentId)) {
                return zlXcxmenu.getMenuid();
            }
        }
        return null;
    }

}
