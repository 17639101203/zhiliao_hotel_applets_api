package com.zhiliao.hotel.controller.invoice;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.invoice.params.InvoiceParam;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "首页_开票接口_章英杰")
@RestController
@RequestMapping("invoice")
@Slf4j
public class ZlInvoiceController {

    @Autowired
    private ZlInvoiceService service;


    // 允许最大的pageSize
    private final static int MAX_PAGE_SIZE = 20;


    @ApiOperation(value = "新增发票抬头方法")
    @UserLoginToken
    @PostMapping("addinvoice")
    public ReturnString addInvoice(@RequestBody InvoiceParam ip, HttpServletRequest request) {
        ZlInvoice invoice = new ZlInvoice();
        try {
            Map<String, Object> map = new HashMap<>();
            // 解析token获取userid
            Long userid = TokenUtil.getUserId(request.getHeader("token"));
            Integer nowTime = DateUtils.javaToPhpNowDateTime();
            invoice.setUserid(userid);        //  UserID
            invoice.setInvoicetype(ip.getInvoicetype());    //  发票类型:1:增值税普通发票;2增值税专用发票
            invoice.setMainbodytype(ip.getMainbodytype());  //发票类型:(主体)1: 个人;2单位
            invoice.setTitle(ip.getTitle());        // 个人真实姓/或单位抬头
            invoice.setTel(ip.getTel());        //  个人或单位电话号码
            invoice.setElectronicpapertype(ip.getElectronicpapertype());    // 发票类型 0 电子发票；1纸质发票
            invoice.setRemark(ip.getRemark());  //备注信息
            invoice.setInvoicetype((byte) 0);   // 开票状态 -1:已取消;0:未开票;1开票中;2已开票
            invoice.setIsdelete(false);     // 是否删除
            invoice.setCreatedate(nowTime);     //添加时间
            invoice.setUpdatedate(nowTime);     //修改时间
            if (ip.getInvoicetype() == 1) {      //个人开票
                service.addInvoice(invoice);
                log.info("增值税普通发票抬头开具成功,invoiceid：" + invoice.getInvoiceid());
                map.put("invoiceid", invoice.getInvoiceid());
                return new ReturnString<>(0, "增值税普通发票抬头开具成功", map);
            } else if (ip.getInvoicetype() == 2) {  //企业开票
                invoice.setIdentifier(ip.getIdentifier());  //单位的纳税人识别号:15/18或20位
                invoice.setBank(ip.getBank());   //开户银行
                invoice.setBankaccountnumber(ip.getBankaccountnumber()); //银行账号
                invoice.setCompanytel(ip.getCompanytel());    //单位电话
                invoice.setCompanyaddress(ip.getCompanyaddress());  //  单位地址
                service.addInvoice(invoice);
                log.info("增值税专用发票抬头开具成功,invoiceid：" + invoice.getInvoiceid());
                map.put("invoiceid", invoice.getInvoiceid());
                return new ReturnString<>(0, "增值税专用发票抬头开具成功", map);
            }
            return new ReturnString<>(-1, "开票类型错误，请重新再试!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("开票失败，请重新再试");
            return new ReturnString<>(-1, "开票失败，请重新再试!");
        }

    }


    @ApiOperation(value = "查询发票抬头方法")
    @UserLoginToken
    @GetMapping("findinvoice/{hotelid}/{pageNo}/{pageSize}")
    public ReturnString findInvoice(HttpServletRequest request, @PathVariable Integer hotelid,
                                    @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        // 判断是否有商家自带的开票二维码
        Map<String, Object> invoiceQrCodeUrlmap = service.findInvoiceQrCodeUrl(hotelid);
        if (!invoiceQrCodeUrlmap.get("InvoiceQrCodeUrl").equals("")) {
            return new ReturnString<>(0, "酒店已有开票二维码", invoiceQrCodeUrlmap);
        }
        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        PageInfoResult<List<Map<String, Object>>> list = service.queryByUserID(userid, pageNo, pageSize);
        if (list == null) {
            return new ReturnString<>(0, "未查询到发票信息！");
        }
        return new ReturnString<>(list);
    }

    @ApiOperation(value = "删除发票抬头方法")
    @UserLoginToken
    @GetMapping("deleteinvoice/{invoiceid}")
    public ReturnString delete(HttpServletRequest request, @PathVariable Integer invoiceid) {
        try {
            // 解析token获取userid
            Long userid = TokenUtil.getUserId(request.getHeader("token"));
            if (invoiceid != null) {
                service.deleteInvoice(userid, invoiceid);
                return new ReturnString<>(0, "发票抬头删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnString<>(-1, "发票抬头删除失败，请重新再试!");

    }


    @ApiOperation(value = "查询发票详情")
    @UserLoginToken
    @GetMapping("findinvoicedetails/{invoiceid}")
    public ReturnString<Map<String, Object>> findinvoicedetails(HttpServletRequest request, @PathVariable Integer invoiceid) {

        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        if (invoiceid != null) {
            Map<String, Object> map = service.findinvoicedetails(userid, invoiceid);
            return new ReturnString<>(map);
        }
        return new ReturnString<>(-1, "参数传递错误，请重新再试");

    }

}
