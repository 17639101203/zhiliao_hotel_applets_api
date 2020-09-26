package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.wxuser.dto.UserVisitHotelLogDTO;
import com.zhiliao.hotel.mapper.ZlUserhotellogMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlUserhotellog;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.service.ZlUserhotellogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-22 17:55
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlUserhotellogServiceImpl implements ZlUserhotellogService {

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    private ZlUserhotellogMapper zlUserhotellogMapper;

    @Override
    public void userVisitHotelLog(Long userid, UserVisitHotelLogDTO userVisitHotelLogDTO) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
        zlWxuserdetail.setUserid(userid);
        zlWxuserdetail = zlWxuserdetailMapper.selectOne(zlWxuserdetail);

        ZlUserhotellog zlUserhotellog = new ZlUserhotellog();
        zlUserhotellog.setUserid(userid);
        zlUserhotellog.setHotelid(userVisitHotelLogDTO.getHotelID());
        List<ZlUserhotellog> zlUserhotellogList = zlUserhotellogMapper.select(zlUserhotellog);
        if (!CollectionUtils.isEmpty(zlUserhotellogList)) {
            List<ZlUserhotellog> collect = zlUserhotellogList.stream().sorted((zlUserhotellog1, zlUserhotellog2) -> {
                return zlUserhotellog1.getStarttime().compareTo(zlUserhotellog2.getStarttime());
            }).collect(Collectors.toList());
            if ((userVisitHotelLogDTO.getStartTime() - collect.get(collect.size() - 1).getEndtime() >= 30)) {
                zlUserhotellog.setNickname(zlWxuserdetail.getRealname());
                zlUserhotellog.setHotelname(userVisitHotelLogDTO.getHotelName());
                zlUserhotellog.setStarttime(userVisitHotelLogDTO.getStartTime());
                zlUserhotellog.setEndtime(userVisitHotelLogDTO.getEndTime());
                zlUserhotellog.setCreatedate(currertTime);
                zlUserhotellogMapper.insertSelective(zlUserhotellog);
            }
        } else {
            zlUserhotellog.setNickname(zlWxuserdetail.getRealname());
            zlUserhotellog.setHotelname(userVisitHotelLogDTO.getHotelName());
            zlUserhotellog.setStarttime(userVisitHotelLogDTO.getStartTime());
            zlUserhotellog.setEndtime(userVisitHotelLogDTO.getEndTime());
            zlUserhotellog.setCreatedate(currertTime);
            zlUserhotellogMapper.insertSelective(zlUserhotellog);
        }
    }

}
