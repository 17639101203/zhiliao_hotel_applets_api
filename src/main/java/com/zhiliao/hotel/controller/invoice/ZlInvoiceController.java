package com.zhiliao.hotel.controller.invoice;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.invoice.params.InvoiceParam;
import com.zhiliao.hotel.model.zlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "开票模块接口")
@RestController
@RequestMapping("invoice")
public class ZlInvoiceController {

    @Autowired
    private ZlInvoiceService service;

    private static final Logger logger = LoggerFactory.getLogger(ZlInvoiceController.class);

    @ApiOperation(value = "新增发票抬头方法")
    @UserLoginToken
    @PostMapping("addinvoice")
    public ReturnString addInvoice(@RequestBody InvoiceParam ip, @RequestParam("token") String token){
        zlInvoice invoice = new zlInvoice();
        try {

            // 解析token获取userid
            Long userid = TokenUtil.getUserId(token);
            invoice.setUserid(userid);        //  UserID
            invoice.setInvoicetype(ip.getInvoicetype());    //  发票类型:1:增值税普通发票;2增值税专用发票
            invoice.setMainbodytype(ip.getMainbodytype());  //发票类型:(主体)1: 个人;2单位
            invoice.setTitle(ip.getTitle());        // 个人真实姓/或单位抬头
            invoice.setTel(ip.getTel());        //  个人或单位电话号码
            invoice.setElectronicpapertype(ip.getElectronicpapertype());    // 发票类型 0 电子发票；1纸质发票
            invoice.setRemark(ip.getRemark());  //备注信息
            invoice.setInvoicetype((byte) 0);   // 开票状态 -1:已取消;0:未开票;1开票中;2已开票
            invoice.setIsdelete(false);     // 是否删除
            invoice.setCreatedate(DateUtils.javaToPhpNowDateTime());     //添加时间
            invoice.setUpdatedate(DateUtils.javaToPhpNowDateTime());     //修改时间
            if(ip.getMainbodytype()==1){      //个人开票
                service.addInvoice(invoice);
                logger.info("增值税普通发票开具成功");
                return new ReturnString(0,"增值税普通发票开具成功");
            }else if(ip.getMainbodytype()==2){  //企业开票
                invoice.setIdentifier(ip.getIdentifier());  //单位的纳税人识别号:15/18或20位
                invoice.setBank(ip.getBank());   //开户银行
                invoice.setBankaccountnumber(ip.getBankaccountnumber()); //银行账号
                invoice.setCompanytel(ip.getCompanytel());    //单位电话
                invoice.setCompanyaddress(ip.getCompanyaddress());  //  单位地址
                service.addInvoice(invoice);
                logger.info("增值税专用发票开具成功");
                return new ReturnString(0,"增值税专用发票开具成功");
            }
            return new ReturnString(1,"开票类型错误，请重新再试");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("开票失败，请重新再试");
            return new ReturnString(1,"开票失败，请重新再试");
        }

    }


    @ApiOperation(value = "查询发票抬头方法")
    @UserLoginToken
    @GetMapping("findinvoice")
    public ReturnString findInvoice(String token){
        Long userid = TokenUtil.getUserId(token);
        List<zlInvoice> list = service.queryByUserID(userid);
        if(list==null){
            return new ReturnString(1,"未查询到发票信息！");
        }
        return new ReturnString(list);
    }

    @ApiOperation(value = "删除发票抬头方法")
    @UserLoginToken
    @GetMapping("deleteinvoice/{invoiceid}")
    public ReturnString delete(@RequestParam("token") String token,@PathVariable Integer invoiceid){
        try {
            Long userid = TokenUtil.getUserId(token);
            if(invoiceid!=null){
                service.deleteInvoice(userid,invoiceid);
                return new ReturnString(0,"发票抬头删除成功");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
            return new ReturnString(1,"发票抬头删除失败，请重新再试");

    }

    @ApiOperation(value = "发票订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "invoicestatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：已取消，0：未开票，1：开票中，2：已开票"),
            @ApiImplicitParam(paramType="query", name="pageNum", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("findByUserId")
    @ResponseBody
    public ReturnString findAllByUserId(String token, Integer invoicestatus, Integer pageNum, Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            List<zlInvoice> invoices = service.findAllByUserId(userId,invoicestatus,pageNum,pageSize);
            return new ReturnString(invoices);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value="订单详情")
    @PostMapping("detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="Integer", name="invoiceid", value="发票订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString repairOrderDetail(Integer invoiceid){
        try {
            zlInvoice invoice = service.orderDetail(invoiceid);
            return new ReturnString(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
