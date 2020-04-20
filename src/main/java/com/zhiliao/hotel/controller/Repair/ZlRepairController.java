package com.zhiliao.hotel.controller.Repair;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.file.UploadFileController;
import com.zhiliao.hotel.model.ZlRepair;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "报修模块接口")
@RestController
@RequestMapping("repair")
public class ZlRepairController {

    @Autowired
    private ZlRepairService service;

    @Autowired
    private UploadFileController fileController;

    private static final Logger logger = LoggerFactory.getLogger(ZlRepairController.class);

    @ApiOperation(value = "添加报修信息方法")
    @PostMapping(value = "addrepair", consumes = { "multipart/*" }, headers = "content-type=multipart/form-data")
    @UserLoginToken
    public ReturnString addrepair(RepairParam repairParam,
                                  @RequestParam("multipartFiles") MultipartFile[] multipartFiles,
                                  @RequestParam String token) {
            ZlRepair repair = new ZlRepair();
        try {
            // 解析token获取userid
            Long userid = TokenUtil.getUserId(token);
            //传入文件分析后，得到文件存放地址  key：filePathBase
            ReturnString returnString = fileController.uploadFile(multipartFiles,token);
            List<String> list = (List) returnString.getData();
            StringBuffer Imgurls  = new StringBuffer();
            list.forEach(item -> {
                Imgurls.append(item+"|");   // 遍历集合，生成图片地址，并用 | 隔开
                System.out.println(item);
            });
            repair.setImgurls(Imgurls.toString());     // 获得图片地址，多个用|隔开
            repair.setUserid(userid);             //  用户ID
            repair.setHotelid(repairParam.getHotelid());        //酒店ID
            repair.setRoomid(repairParam.getRoomid());      // 房间ID
            repair.setRoomnumber(repairParam.getRoomnumber());      //房间号
            repair.setRemark(repairParam.getRemark());      //  备注信息
            repair.setRepairstatus((byte)0);        // 维修状态:0未维修;1维修中;2已维修
            repair.setIsdelete(false);         // 删除状态:false正常;true删除;
            repair.setCreatedate(DateUtils.javaToPhpNowDateTime());     //添加日期
            repair.setUpdatedate(DateUtils.javaToPhpNowDateTime());     //修改日期
            service.addRepairMsg(repair,repairParam.getHotelname());
            return new ReturnString(0, "报修信息添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("报修信息添加失败");
            return new ReturnString(1, "报修信息添加失败，请重新再试");
        }
    }


    @ApiOperation(value = "查询报修订单详情方法")
    @PostMapping("queryRepairOrder")
    @UserLoginToken
    public ReturnString addrepair(@RequestParam String token){
        try {
            // 解析token获取userid
            Long userid = TokenUtil.getUserId(token);
            ZlRepairorder zlRepairorder = service.queryRepairOrder(userid);
            return new ReturnString(zlRepairorder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("报修订单信息查询失败");
            return new ReturnString(1, "报修订单信息查询失败，请重新再试");
        }

    }


}
