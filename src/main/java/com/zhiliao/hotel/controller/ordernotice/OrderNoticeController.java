package com.zhiliao.hotel.controller.ordernotice;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.OrderNoticeConstant;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.ordernotice.vo.PhpNoticeVO2;
import com.zhiliao.hotel.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-22 15:34
 **/
@Api(tags = "订单通知_姬慧慧")
@RestController
@RequestMapping("orderNotice")
public class OrderNoticeController {

    private static final Logger logger = LoggerFactory.getLogger(OrderNoticeController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${weChat.appid}")
    private String APP_ID;
    @Value("${weChat.secret}")
    private String SECRET;
    @Value("${weChat.gzhappid}")
    private String GZHAPP_ID;
    @Value("${weChat.templateId}")
    private String TEMPLATE_ID;

    @ApiOperation(value = "订单通知_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", dataType = "int", required = true, value = "消息类型:1 预订订单,2 商超订单"),
    })
    @PostMapping("sendOrderNotice2/{type}")
    @PassToken
    public ReturnString sendOrderNotice2(@PathVariable("type") Integer type, @Validated @RequestBody PhpNoticeVO2 phpNoticeVO) {

        if (ObjectUtils.toString(phpNoticeVO.getData(), null) == null) {
            logger.info("data数据不能为空,请重新填写...");
            return new ReturnString("data数据不能为空,请重新填写...");
        }

        logger.info("Php端传过来的用于通知小程序端的消息:" + phpNoticeVO.toString());
        String params = "grant_type=client_credential&appid=" + APP_ID + "&secret=" + SECRET;

        String accessToken = null;
        if (redisTemplate.hasKey("XcxAccessToken")) {
            //从缓存中拿accessToken
            accessToken = (String) redisTemplate.opsForValue().get("XcxAccessToken");
        } else {
            logger.info("请求微信接口获取access_token,链接为" + OrderNoticeConstant.getAccessToken + "?" + params);
            String resultJson = HttpUtil.sendGet(OrderNoticeConstant.getAccessToken, params);
            if (StringUtils.isNoneBlank(resultJson)) {
                Map map = JSON.parseObject(resultJson);
                accessToken = (String) map.get("access_token");
                redisTemplate.opsForValue().set("XcxAccessToken", accessToken, RedisKeyConstant.ACCESSTOKEN, TimeUnit.SECONDS);
            }
        }

//        String accessToken = (String) redisTemplate.opsForValue().get("XcxAccessToken");

        if (!StringUtils.isNoneBlank(accessToken)) {
            logger.info("从缓存中获取access_token失败...");
            return new ReturnString("从缓存中获取access_token失败...");
        }

        if (type == 1) {
            phpNoticeVO.setTemplate_id(TEMPLATE_ID);
        }
        if (type == 2) {
            phpNoticeVO.setTemplate_id(TEMPLATE_ID);
        }

        logger.info("获取到的access_token为" + accessToken);

        //获取模板列表
//        String gettemplateJson = HttpUtil.sendGet(OrderNoticeConstant.gettemplate, "access_token=" + accessToken);
//        System.err.println(gettemplateJson);

        Map sendNoticeResultMap = sendNotice(phpNoticeVO, accessToken);

        logger.info("返回的消息代码:" + sendNoticeResultMap.get("errcode"), "返回的消息内容:" + sendNoticeResultMap.get("errmsg"));
        if (sendNoticeResultMap.get("errcode").equals(0)) {
            logger.info("消息发送成功,请用户查收...");
            return new ReturnString(0, "消息发送成功,请用户查收...");
        } else {
            if (sendNoticeResultMap.get("errcode").equals(40001)) {
                logger.info("请求微信接口获取access_token,链接为" + OrderNoticeConstant.getAccessToken + "?" + params);
                String resultJson = HttpUtil.sendGet(OrderNoticeConstant.getAccessToken, params);
                if (StringUtils.isNoneBlank(resultJson)) {
                    Map map = JSON.parseObject(resultJson);
                    accessToken = (String) map.get("access_token");
                    redisTemplate.opsForValue().set("XcxAccessToken", accessToken, RedisKeyConstant.ACCESSTOKEN, TimeUnit.SECONDS);
                }
                Map map = sendNotice(phpNoticeVO, accessToken);
                if (map.get("errcode").equals(0)) {
                    logger.info("消息发送成功,请用户查收...");
                    return new ReturnString(0, "消息发送成功,请用户查收...");
                } else {
                    logger.info("消息发送失败,错误码为:" + sendNoticeResultMap.get("errcode"));
                    return new ReturnString("消息发送失败,错误码为:" + sendNoticeResultMap.get("errcode"));
                }
            } else {
                logger.info("消息发送失败,错误码为:" + sendNoticeResultMap.get("errcode"));
                return new ReturnString("消息发送失败,错误码为:" + sendNoticeResultMap.get("errcode"));
            }
        }
    }

    /**
     * 发送通知的方法
     *
     * @param phpNoticeVO
     * @param accessToken
     * @return
     */
    private static Map sendNotice(PhpNoticeVO2 phpNoticeVO, String accessToken) {

        Gson gson = new Gson();
        String argsJSONStr = gson.toJson(phpNoticeVO);

        String sendNotice = OrderNoticeConstant.sendNotice2 + "?access_token=" + accessToken;
        logger.info("小程序端发送消息通知链接为:" + sendNotice + "参数为:" + argsJSONStr);

        System.err.println(argsJSONStr);
        String sendNoticeResultJson = null;
        sendNoticeResultJson = HttpUtil.sendPostJson(sendNotice, argsJSONStr);
        Map sendNoticeResultMap = JSON.parseObject(sendNoticeResultJson);
        return sendNoticeResultMap;
    }

}
