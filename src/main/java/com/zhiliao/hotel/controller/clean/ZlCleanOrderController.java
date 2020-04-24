package com.zhiliao.hotel.controller.clean;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.service.ZlCleanOrderService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zhiliao.hotel.utils.DateUtils.*;
import static com.zhiliao.hotel.utils.OrderIDUtil.createOrderID;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:49
 * @Description:
 */
@Api(tags = "清扫接口")
@RestController
@RequestMapping("clean")
public class ZlCleanOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ZlCleanOrderController.class);

    private final ZlCleanOrderService zlCleanOrderService;

    @Autowired
    public ZlCleanOrderController(ZlCleanOrderService zlCleanOrderService) {
        this.zlCleanOrderService = zlCleanOrderService;
    }

    @ApiOperation(value = "清扫下单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "hotelID", dataType = "int", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "query", name = "hotelName", dataType = "String", required = true, value = "酒店名"),
            @ApiImplicitParam(paramType = "query", name = "roomID", dataType = "int", required = true, value = "房间ID"),
            @ApiImplicitParam(paramType = "query", name = "roomNumber", dataType = "String", required = true, value = "房间号"),
            @ApiImplicitParam(paramType = "query", name = "comeformID", dataType = "int", required = true, value = "来自1小程序C端，2小程序B端，3公众号，4民宿，5好评返现，6分时酒店"),
            @ApiImplicitParam(paramType = "query", name = "bookDay", dataType = "String", required = true, value = "今天/明天"),
            @ApiImplicitParam(paramType = "query", name = "bookDate", dataType = "String", required = true, value = "预约时间 格式HH:mm"),
            @ApiImplicitParam(paramType = "query", name = "remark", dataType = "String", required = false, value = "其他需求备注")
    })
    @PostMapping("addCleanOrder")
    @UserLoginToken
    public ReturnString addCleanOrder(String token, Integer hotelID, String hotelName, Integer roomID, String roomNumber, Integer comeformID, String bookDay, String bookDate, String remark) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");   //设置日期格式
//        String uuid = UUID.randomUUID().toString().replaceAll("-","");   //把uuid生成的"-"去掉
//        String serialNumber = hotelID +""+ roomID + df.format(new Date()) + uuid.substring(0,6);   //订单号:酒店ID+房间ID+日期+uuid的前6位
//        int nowTime = Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000));   //获取当前时间戳 单位:秒

        ZlCleanOrder zlCleanOrder = new ZlCleanOrder();
        zlCleanOrder.setUserid(TokenUtil.getUserId(token));   //用户ID  根据token获取userId
        zlCleanOrder.setSerialnumber(createOrderID("QS"));   //订单编号
        zlCleanOrder.setHotelid(hotelID);   //酒店ID
        zlCleanOrder.setHotelname(hotelName);   //酒店名
        zlCleanOrder.setRoomid(roomID);   //房间ID
        zlCleanOrder.setRoomnumber(roomNumber);   //房间号
        zlCleanOrder.setComeformid(comeformID);   //来自1小程序C端，2小程序B端，3公众号, 4民宿，5好评返现，6分时酒店
        Long bookTime = null;
        if (bookDay.equals("今天")) {
            bookTime = getTimeByDate(getDateByString() + " " + bookDate + ":00") / 1000;
        } else if (bookDay.equals("明天")) {
            bookTime = getTimeByDate(getDateByString() + " " + bookDate + ":00") / 1000 + 86400;
        }
        zlCleanOrder.setBookdate(bookTime.intValue());   //预定时间
        zlCleanOrder.setRemark(remark);   //其他需求备注
        //zlCleanOrder.setOrderstatus((byte) 0);   //订单状态 -1:已取消 0:等待确认 1:已确认 2:已处理
        //zlCleanOrder.setIsdelete(false);   //删除状态 0:正常 1:删除
        zlCleanOrder.setCreatedate(DateUtils.javaToPhpNowDateTime());   //下单时间
        //zlCleanOrder.setUpdatedate(0);   //支付/取消时间

        Integer res = zlCleanOrderService.addCleanOrder(zlCleanOrder);

        if (res > 0) {
            return new ReturnString(0, "下单成功");
        } else {
            logger.error("下单失败");
            return new ReturnString(-1, "下单失败");
        }
    }

    @ApiOperation(value = "查询清扫订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "serialNumber", dataType = "String", required = true, value = "订单编号"),
    })
    @PostMapping("selectCleanDetails")
    @UserLoginToken
    public ReturnString selectCleanDetails(String token, String serialNumber) {

        Long userID = TokenUtil.getUserId(token);   //用户ID  根据token获取userId
        ZlCleanOrder zlCleanOrder = zlCleanOrderService.selectCleanDetails(userID, serialNumber);

        if (zlCleanOrder.getUserid() != null) {
            return new ReturnString(0, "查询成功");
        } else {
            logger.error("查询失败");
            return new ReturnString(-1, "查询失败");
        }
    }

}
