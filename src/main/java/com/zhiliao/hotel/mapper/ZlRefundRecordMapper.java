package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRefundRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-14 20:33
 **/
public interface ZlRefundRecordMapper extends Mapper<ZlRefundRecord> {

    void updateRefundStatus2(@Param("orderId") Long orderId, @Param("currertTime") Integer currertTime);

    void uploadExpressInfo(@Param("orderid") Long orderid, @Param("expressid") Short expressid, @Param("usertracknumber") String usertracknumber, @Param("currertTime") Integer currertTime);

    void updateRefundStatus3(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    void updateRefundStatus4(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    void updateRefundStatus4Two(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);
}
