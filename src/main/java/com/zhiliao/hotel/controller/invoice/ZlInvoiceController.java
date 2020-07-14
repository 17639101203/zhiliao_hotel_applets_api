package com.zhiliao.hotel.controller.invoice;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderParam;
import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.controller.invoice.params.InvoiceParam;
import com.zhiliao.hotel.controller.invoice.vo.InvoiceOrderToPhpVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.model.ZlHotel;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlInvoiceOrder;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.ZlInvoiceService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.PushInfoToPhpUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(tags = "首页_开票接口_章英杰")
@RestController
@RequestMapping("invoice")
@Slf4j
public class ZlInvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(ZlInvoiceController.class);

    @Autowired
    private ZlInvoiceService zlInvoiceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private ZlHotelMapper zlHotelMapper;

    // 允许最大的pageSize
    private final static int MAX_PAGE_SIZE = 20;


    @ApiOperation(value = "保存/修改发票抬头")
    @UserLoginToken
//    @PassToken
    @PostMapping("addInvoice")
    public ReturnString addInvoice(@RequestBody InvoiceParam ip, HttpServletRequest request) {
        ZlInvoice invoice = new ZlInvoice();
        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
//        Long userid = System.currentTimeMillis();
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        invoice.setUserid(userid);        //  UserID
        invoice.setInvoicetype(ip.getInvoicetype());    //  发票类型:1:增值税普通发票;2增值税专用发票
        invoice.setMainbodytype(ip.getMainbodytype());  //发票类型:(主体)1: 个人;2单位
        invoice.setTitle(ip.getTitle());        // 个人真实姓/或单位抬头
        invoice.setTel(ip.getTel());        //  个人或单位电话号码
        invoice.setElectronicpapertype(ip.getElectronicpapertype());    // 发票类型 0 电子发票；1纸质发票
        invoice.setRemark(ip.getRemark());  //备注信息
        invoice.setIdentifier(ip.getIdentifier());  //单位的纳税人识别号:15/18或20位
        invoice.setBank(ip.getBank());   //开户银行
        invoice.setBankaccountnumber(ip.getBankaccountnumber()); //银行账号
        invoice.setCompanytel(ip.getCompanytel());    //单位电话
        invoice.setCompanyaddress(ip.getCompanyaddress());  //  单位地址
        invoice.setCreatedate(nowTime);     //添加时间
        invoice.setUpdatedate(nowTime);     //修改时间
        if (ip.getSaveType().equals("1")) {       //  保存类型为新增
            if (ip.getInvoicetype() == 1) {      //个人开票
                Map<String, Object> map = zlInvoiceService.addInvoice(invoice);
                return new ReturnString<>(map);
            } else if (ip.getInvoicetype() == 2) {  //企业开票
                Map<String, Object> map = zlInvoiceService.addInvoice(invoice);
                return new ReturnString<>(map);
            }
            return new ReturnString<>(-1, "开票类型错误，请重新再试!");
        } else if (ip.getSaveType().equals("2")) { //保存类型为修改
            invoice.setInvoiceid(ip.getInvoiceid());    // 发票ID
            zlInvoiceService.changeInvoice(invoice);
            return new ReturnString<>(0, "发票抬头修改成功");
        }
        return new ReturnString<>(-1, "保存类型错误，请重新再试！");
    }


    @ApiOperation(value = "开具发票订单")
    @UserLoginToken
//    @PassToken
    @PostMapping("addInvoiceOrder")
    public ReturnString<Map<String, Object>> addInvoiceOrder(@RequestBody InvoiceOrderParam invoiceOrderParam, HttpServletRequest request) {
        ZlInvoiceOrder zlInvoiceOrder = new ZlInvoiceOrder();
        Map<String, Object> map = new HashMap<>();
        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userid);
        ZlWxuser wxuser = zlWxuserMapper.selectOne(zlWxuser);
//        Long userid = System.currentTimeMillis();
        // 生成订单ID
        String invoiceOrderNumber = OrderIDUtil.createOrderID("FP");
        map.put("invoiceordernumber", invoiceOrderNumber);
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        zlInvoiceOrder.setInvoiceordernumber(invoiceOrderNumber);    // 订单ID
        zlInvoiceOrder.setInvoiceid(invoiceOrderParam.getInvoiceid());   // 发票ID
        zlInvoiceOrder.setHotelid(invoiceOrderParam.getHotelid());        //酒店ID
        zlInvoiceOrder.setRoomnumber(invoiceOrderParam.getRoomnumber());  //房间号
        zlInvoiceOrder.setUserid(userid);        //  UserID
        zlInvoiceOrder.setInvoicetype(invoiceOrderParam.getInvoicetype());    //  发票类型:1:增值税普通发票;2增值税专用发票
        zlInvoiceOrder.setMainbodytype(invoiceOrderParam.getMainbodytype());  //发票类型:(主体)1: 个人;2单位
        zlInvoiceOrder.setTitle(invoiceOrderParam.getTitle());        // 个人真实姓/或单位抬头
        zlInvoiceOrder.setTel(invoiceOrderParam.getTel());        //  个人或单位电话号码
        zlInvoiceOrder.setElectronicpapertype(invoiceOrderParam.getElectronicpapertype());    // 发票类型 0 电子发票；1纸质发票
        zlInvoiceOrder.setRemark(invoiceOrderParam.getRemark());  //备注信息
        zlInvoiceOrder.setCreatedate(nowTime);     //添加时间
        zlInvoiceOrder.setUpdatedate(nowTime);     //修改时间
        zlInvoiceOrder.setUsername(wxuser.getNickname());
        ZlHotel zlHotel = zlHotelMapper.getById(invoiceOrderParam.getHotelid());
        zlInvoiceOrder.setHotelname(zlHotel.getHotelName());
        if (invoiceOrderParam.getInvoicetype() == 1) {      //个人开票
            zlInvoiceService.addinvoiceOrder(zlInvoiceOrder);
            logger.info("开票订单插入数据库完成,订单id:" + zlInvoiceOrder.getInvoiceorderid());

            // 推送消息
            InvoiceOrderToPhpVO invoiceOrderToPhpVO = zlInvoiceService.selectToPhp(zlInvoiceOrder.getInvoiceorderid());
//            PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_SERVICE_GOODS, invoiceOrderToPhpVO);
            OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
            orderPhpSendVO.setForm("java");
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_INVOICE_ORDER);
            orderPhpSendVO.setMessage(invoiceOrderToPhpVO);
            Gson gson = new Gson();
            String orderStr = gson.toJson(orderPhpSendVO);
            stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_INVOICE_ORDER, orderStr);
            logger.info("推送开票订单到redis通知php后台人员完成,订单信息:" + invoiceOrderToPhpVO);

            map.put("invoiceorderid", zlInvoiceOrder.getInvoiceorderid());
            return new ReturnString<>(0, "增值税普通发票开票成功", map);
        } else if (invoiceOrderParam.getInvoicetype() == 2) {  //企业开票
            zlInvoiceOrder.setIdentifier(invoiceOrderParam.getIdentifier());  //单位的纳税人识别号:15/18或20位
            zlInvoiceOrder.setBank(invoiceOrderParam.getBank());   //开户银行
            zlInvoiceOrder.setBankaccountnumber(invoiceOrderParam.getBankaccountnumber()); //银行账号
            zlInvoiceOrder.setCompanytel(invoiceOrderParam.getCompanytel());    //单位电话
            zlInvoiceOrder.setCompanyaddress(invoiceOrderParam.getCompanyaddress());  //  单位地址
            zlInvoiceService.addinvoiceOrder(zlInvoiceOrder);
            logger.info("开票订单插入数据库完成,订单id:" + zlInvoiceOrder.getInvoiceorderid());

            // 推送消息
            InvoiceOrderToPhpVO invoiceOrderToPhpVO = zlInvoiceService.selectToPhp(zlInvoiceOrder.getInvoiceorderid());
//            PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_SERVICE_GOODS, invoiceOrderToPhpVO);
            OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
            orderPhpSendVO.setForm("java");
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_INVOICE_ORDER);
            orderPhpSendVO.setMessage(invoiceOrderToPhpVO);
            Gson gson = new Gson();
            String orderStr = gson.toJson(orderPhpSendVO);
            stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_INVOICE_ORDER, orderStr);
            logger.info("推送开票订单到redis通知php后台人员完成,订单信息:" + invoiceOrderToPhpVO);

            map.put("invoiceorderid", zlInvoiceOrder.getInvoiceorderid());
            return new ReturnString<>(0, "增值税专用发票开票成功", map);
        }
        return new ReturnString<>(-1, "开票类型错误，请重新再试!");
    }


    @ApiOperation(value = "查询发票抬头")
    @UserLoginToken
//    @PassToken
    @GetMapping("findInvoiceHeads/{hotelid}/{pageNo}/{pageSize}")
    public ReturnString findInvoiceHeads(HttpServletRequest request, @PathVariable Integer hotelid,
                                         @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        // 判断是否有商家自带的开票二维码
        String invoiceQrCodeUrl = zlInvoiceService.findInvoiceQrCodeUrl(hotelid);
        if (StringUtils.isNoneBlank(invoiceQrCodeUrl)) {
            HashMap hashMap = new HashMap();
            hashMap.put("InvoiceQrCodeUrl", invoiceQrCodeUrl);
            return new ReturnString<>(0, "酒店已有开票二维码", hashMap);
        }
        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
//        Long userid = 74L;
        pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        PageInfoResult<List<Map<String, Object>>> list = zlInvoiceService.queryByUserID(userid, pageNo, pageSize);
        if (list == null) {
            return new ReturnString<>(0, "未查询到发票信息！");
        }
        return new ReturnString<>(list);
    }

    @ApiOperation(value = "删除发票抬头")
    @UserLoginToken
    @PostMapping("deleteInvoice/{invoiceid}")
    public ReturnString deleteInvoice(@PathVariable Integer invoiceid) {
        zlInvoiceService.deleteInvoice(invoiceid);
        return new ReturnString<>(0, "发票抬头删除成功");
    }


    @ApiOperation(value = "查询发票抬头详情")
    @UserLoginToken
    @GetMapping("findInvoiceDetails/{invoiceid}")
    public ReturnString<Map<String, Object>> findInvoiceDetails(HttpServletRequest request, @PathVariable Integer invoiceid) {
        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        Map<String, Object> map = zlInvoiceService.findinvoicedetails(userid, invoiceid);
        return new ReturnString<>(map);
    }


    @ApiOperation(value = "查询发票订单详情")
    @UserLoginToken
    @GetMapping("findInvoiceOrderDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "invoiceorderid", dataType = "Long", required = true, value = "发票订单ID")
    })
    public ReturnString findInvoiceOrderDetails(Long invoiceorderid) {
        if (invoiceorderid == null || "".equals(invoiceorderid)) {
            return new ReturnString<>(-1, "订单号为空，请重新再试");
        }

        try {
            InvoiceOrderVO vo = zlInvoiceService.findInvoiceOrderdetail(invoiceorderid);
            return new ReturnString(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询发票订单详情失败!");
        }
    }


    @ApiOperation(value = "取消开票订单预约")
    @PostMapping("cancelInvoiceOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "invoiceorderid", dataType = "Long", required = true, value = "发票订单ID")
    })
    @UserLoginToken
    public ReturnString cancelInvoiceOrder(Long invoiceorderid) {
        if (invoiceorderid == null || "".equals(invoiceorderid)) {
            return new ReturnString<>(-1, "订单号为空，请重新再试");
        }
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        try {
            zlInvoiceService.cancelInvoiceOrder(invoiceorderid, nowTime);
            return new ReturnString<>(0, "预约已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>("取消预约失败!");
        }
    }

    @ApiOperation(value = "删除开票订单")
    @PostMapping("deleteInvoiceOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "invoiceorderid", dataType = "Long", required = true, value = "发票订单ID")
    })
    @UserLoginToken
    public ReturnString deleteInvoiceOrder(Long invoiceorderid) {
        if (invoiceorderid == null || "".equals(invoiceorderid)) {
            return new ReturnString<>(-1, "订单号为空，请重新再试");
        }

        try {
            zlInvoiceService.deleteInvoiceOrder(invoiceorderid);
            return new ReturnString<>(0, "订单已删除!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>("删除订单失败!");
        }
    }

}
