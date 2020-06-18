package com.zhiliao.hotel.controller.Repair;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.file.UploadFileController;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "首页_报修模块接口_章英杰")
@RestController
@RequestMapping("repair")
@Slf4j
public class ZlRepairController {

    @Autowired
    private ZlRepairService service;

    @Autowired
    private UploadFileController fileController;


    @ApiOperation(value = "添加报修信息")
    @PostMapping(value = "addrepair", consumes = {"multipart/*"}, headers = "content-type=multipart/form-data")
    @UserLoginToken
    public ReturnString addrepair(RepairParam repairParam,
                                  @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile,
                                  HttpServletRequest request) {

        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        try {
            Map<String, Object> map = service.addRepairMsg(userid, repairParam, multipartFile);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("添加报修信息失败!");
        }

//        Integer now = DateUtils.javaToPhpNowDateTime();
//        String serialNumber = OrderIDUtil.createOrderID("");
//        Map<String, Object> map = new HashMap<>();
//        map.put("serialnumber", serialNumber);
//        ZlRepairorder zlRepairorder = new ZlRepairorder();
//        Long userid = TokenUtil.getUserId(request.getHeader("token"));
//        zlRepairorder.setUserid(userid);
//        zlRepairorder.setHotelname(repairParam.getHotelname());  // 酒店名
//        zlRepairorder.setHotelid(repairParam.getHotelid());     // 酒店ID
//        zlRepairorder.setRoomid(repairParam.getRoomid());       // 客房ID
//        zlRepairorder.setRoomnumber(repairParam.getRoomnumber());   //客房号
//        zlRepairorder.setRemark(repairParam.getRemark());       // 备注信息
//        zlRepairorder.setSerialnumber(serialNumber);  //订单号
//        zlRepairorder.setCreatedate(now);
//        zlRepairorder.setUpdatedate(now);
//        ReturnString<List<String>> returnString = fileController.uploadFile(multipartFiles);
//        List<String> list = returnString.getData();
//        StringBuffer Imgurls = new StringBuffer();
//        list.forEach(item -> {
//            Imgurls.append(item + "|");   // 遍历集合，生成图片地址，并用 | 隔开
//            log.info("【报修图片地址：】" + Imgurls);
//        });
//        zlRepairorder.setImgurls(Imgurls.toString());     // 获得图片地址，多个用|隔开
//
//        try {
//            service.addRepairMsg(zlRepairorder);
//            map.put("orderID", zlRepairorder.getOrderid());
//            return new ReturnString<>(0, "报修已提交", map);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ReturnString("添加报修信息失败");
//        }
    }


    @ApiOperation(value = "查询报修订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "报修订单ID")
    })
    @GetMapping("findRepairOrder/{orderID}")
    @UserLoginToken
    public ReturnString<Map<String, Object>> findRepairOrder(@PathVariable("orderID") Long orderID) {
        try {
            Map<String, Object> map = service.findRepairOrder(orderID);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询报修订单详情");
        }
    }


    @ApiOperation(value = "取消报修预约")
    @PostMapping("cancelRepairOrder/{serialnumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "报修订单ID")
    })
    @UserLoginToken
    public ReturnString cancelRepairOrder(@PathVariable("orderID") Long orderID) {
        Integer nowTime = DateUtils.javaToPhpNowDateTime();

        try {
            service.cancelRepairOrder(orderID, nowTime);
            return new ReturnString<>(0, "预约已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("预约取消失败");
        }
    }

    @ApiOperation(value = "用户删除报修订单_徐向向")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("userDeleteRepairOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString userDeleteRepairOrder(@PathVariable("orderID") Long orderID) {
        try {
            service.userDeleteRepairOrder(orderID);
            return new ReturnString("用户删除报修成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("用户删除报修失败!");
        }
    }

}
