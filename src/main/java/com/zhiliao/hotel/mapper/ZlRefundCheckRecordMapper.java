package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myOrder.vo.OrderRefundHistoryVO;
import com.zhiliao.hotel.model.ZlRefundCheckRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-14 20:35
 **/
public interface ZlRefundCheckRecordMapper extends Mapper<ZlRefundCheckRecord> {

    List<OrderRefundHistoryVO> selectZlRefundCheckRecordList(@Param("orderId") Long orderId);

}
