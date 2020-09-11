package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlReservelogMapper;
import com.zhiliao.hotel.model.ZlReservelog;
import com.zhiliao.hotel.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-03 16:06
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private ZlReservelogMapper zlReservelogMapper;

    @Override
    public void cancelOrderLog(Long orderid, Integer hotelid, Integer createdate, Byte moldtype) {

        int currentTime = Math.toIntExact(System.currentTimeMillis() / 1000);
        ZlReservelog zlReservelog = new ZlReservelog();
        zlReservelog.setOrderid(orderid);
        zlReservelog.setHotelid(hotelid);
        zlReservelog.setBooktime(currentTime);
        zlReservelog.setOperatetime(currentTime);
        zlReservelog.setUsetime(currentTime - createdate);
        zlReservelog.setOperatetype(false);
        zlReservelog.setOrdertype(moldtype);

        zlReservelogMapper.insertSelective(zlReservelog);
    }
}
