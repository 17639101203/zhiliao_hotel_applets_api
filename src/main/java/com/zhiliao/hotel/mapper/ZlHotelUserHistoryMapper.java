package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelUserHistory;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlHotelUserHistoryMapper extends Mapper<ZlHotelUserHistory> {

    /**
     * 获取入住酒店历史列表
     */
    List<ZlHotelUserHistory> getHotelHistoryList(@Param("userid") Long userid);

    void userDeleteHotelHistory(@Param("recId") Long recId);
}
