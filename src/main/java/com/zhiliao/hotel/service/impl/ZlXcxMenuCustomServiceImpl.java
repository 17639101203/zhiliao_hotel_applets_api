package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.service.ZlXcxMenuCustomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 小程序菜单业务实现类
 *  @author  chenrong
 * @created date 2020/4/14
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class ZlXcxMenuCustomServiceImpl implements ZlXcxMenuCustomService {
}
