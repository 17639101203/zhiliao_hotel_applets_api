package com.zhiliao.hotel.controller.serviceorder;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.serviceorder.params.ServiceorderCommitParams;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderCommitVo;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderInfoVo;
import com.zhiliao.hotel.service.ZlServiceorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "首页_客房服务订单接口_高翔")
@RestController
@RequestMapping("/serviceorder")
public class ZlServiceorderController {

    @Resource
    private ZlServiceorderService zlServiceorderService;

    /**
     * 客房服务订单提交
     */
    @ApiOperation(value = "高翔_客房服务订单提交")
//    @UserLoginToken
    @PassToken
    @PostMapping("serviceorderSubmit")
    public ReturnString<ServiceorderCommitVo> serviceorderSubmit(HttpServletRequest request, @RequestBody ServiceorderCommitParams scp){
        String token = request.getHeader("token");
        try {
            //生成客房服务订单
            ServiceorderCommitVo serviceorderCommit = zlServiceorderService.serviceorderSubmit(token, scp);
            return new ReturnString<>(0, "订单提交成功", serviceorderCommit);
        }catch (RuntimeException r){
            return new ReturnString(r.getMessage());
        }
    }

    /**
     * 获取客房服务订单详情
     */
    @ApiOperation(value = "高翔_获取客房服务订单详情")
    @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "int", required = true, value = "客房服务订单id")
//    @UserLoginToken
    @PassToken
    @GetMapping("getServiceorderInfo/{orderId}")
    public ReturnString<ServiceorderInfoVo> getServiceorderInfo(@PathVariable Long orderId){
        try {
            ServiceorderInfoVo serviceorderInfoVo = zlServiceorderService.getServiceorderInfo(orderId);
            return new ReturnString(serviceorderInfoVo);
        }catch (RuntimeException r){
            return new ReturnString(r.getMessage());
        }
    }
}
