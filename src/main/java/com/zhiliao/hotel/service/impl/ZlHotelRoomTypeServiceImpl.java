package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.service.ZlHotelRoomTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *   酒店客房房型业务实现类
 * @author chenrong
 * @created date 2020/4/14
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelRoomTypeServiceImpl implements ZlHotelRoomTypeService {
}
