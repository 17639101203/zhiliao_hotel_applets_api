package com.zhiliao.hotel.service.impl;

import com.google.common.collect.Lists;
import com.zhiliao.hotel.mapper.ZlXcxMenuMapper;
import com.zhiliao.hotel.model.ZlXcxmenu;
import com.zhiliao.hotel.service.ZlXcxMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 小程序菜单业务实现类
 * @author chenrong
 * @created date 2020/4/14
 */

@Service
public class ZlXcxMenuServiceImpl implements ZlXcxMenuService {

    @Autowired
    private ZlXcxMenuMapper zlXcxMenuMapper;

    @Override
    public List<ZlXcxmenu> getMenuList(String hotelId) {
        List<ZlXcxmenu> menuList = zlXcxMenuMapper.getMenuList(hotelId);
        if (!CollectionUtils.isEmpty(menuList)) {
            List<ZlXcxmenu> zlXcxMenuArrayList = menuList.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ZlXcxmenu::getMenuname))), ArrayList::new)
            );

            List<ZlXcxmenu> collect = zlXcxMenuArrayList.stream().filter(a -> a.getIsdelete()==0).collect(Collectors.toList());

            return collect;
        }
        return Lists.newArrayList();
    }
}
