package com.zhiliao.hotel.controller.weixinuser;

import com.alibaba.fastjson.JSONObject;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.SjWeixinuser;
import com.zhiliao.hotel.service.SjWeixinuserService;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiegege on 2019/10/14.
 */

@Api(tags = "微信用户接口")
@RestController
@RequestMapping("weixinuser")
public class SjWeixinuserController {

    private static final Logger logger = LoggerFactory.getLogger(SjWeixinuserController.class);

    @Value("${weChat.appid}")
    private String APP_ID;
    @Value("${weChat.secret}")
    private String SECRET;

    private SjWeixinuserService sjWeixinuserService;
    private TokenUtil tokenUtil;
    private RedisCommonUtil redisCommonUtil;

    @Autowired
    public SjWeixinuserController(SjWeixinuserService sjWeixinuserService, TokenUtil tokenUtil, RedisCommonUtil redisCommonUtil) {
        this.sjWeixinuserService = sjWeixinuserService;
        this.tokenUtil = tokenUtil;
        this.redisCommonUtil = redisCommonUtil;
    }

    @ApiOperation(value = "微信用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "code"),
            @ApiImplicitParam(paramType = "query", name = "encryptedData", dataType = "String", required = true, value = "加密秘钥"),
            @ApiImplicitParam(paramType = "query", name = "iv", dataType = "String", required = true, value = "偏移量")
    })
    @PassToken
    @PostMapping("weixinUserLogin")
    public ReturnString weixinUserLogin(String code, String encryptedData, String iv) {
        try {
            logger.info("开始请求->参数->code：" + code + "|加密秘钥：" + encryptedData + "|偏移量：" + iv);
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            JSONObject res = getJsonObject(url);
            if (res != null && res.get("errcode") != null) {
                return new ReturnString("解析失败");
            }
            String openid = res.getString("openid");
            // 通过openid查找用户信息
            SjWeixinuser sjWeixinuser = sjWeixinuserService.findWeixinuserByOpenId(openid);
            if (sjWeixinuser != null) {
                Map<String, Object> dataMap = returnUserInfoData(sjWeixinuser);
                return new ReturnString(dataMap);
            } else {
                // TODO: 相关注册操作
                JSONObject res1 = getUserInfo(encryptedData, String.valueOf(res.get("session_key")), iv);
                if (res1 == null) {
                    return new ReturnString("解析失败");
                }
                sjWeixinuser = new SjWeixinuser();
                sjWeixinuser.setOpenid(openid);
                sjWeixinuser.setUnionid(res1.getString("unionId"));
                sjWeixinuser.setAddtime(new Date().getTime());
                sjWeixinuser.setUpdatatime(new Date().getTime());
                sjWeixinuser.setNicknname(res1.getString("nickName"));
                sjWeixinuser.setHeadimgurl(res1.getString("avatarUrl"));
                sjWeixinuser.setSex(res1.getByte("gender"));
                sjWeixinuser.setProvince(res1.getString("province"));
                sjWeixinuser.setCity(res1.getString("city"));
                // 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
                sjWeixinuser.setComeformid(1);
                sjWeixinuserService.addWeixinuser(sjWeixinuser);
                Map<String, Object> dataMap = returnUserInfoData(sjWeixinuser);
                return new ReturnString(dataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("解析失败");
        }
    }

    private Map<String, Object> returnUserInfoData(SjWeixinuser sjWeixinuser) {
        Map<String, Object> dataMap = new HashMap<>();
        String token = tokenUtil.getToken(sjWeixinuser);
        dataMap.put("token", token);
        // 头像
        dataMap.put("headImgUrl", sjWeixinuser.getHeadimgurl());
        // 微信昵称
        dataMap.put("nickName", sjWeixinuser.getNicknname());
        // 登录成功设置token过期时间 存3天
        redisCommonUtil.setCache(sjWeixinuser.getOpenid(), token, 60 * 60 * 24 * 3);
        return dataMap;
    }

    @UserLoginToken
    @PostMapping("test")
    public ReturnString test(String token) {
        Integer weixinuserId = tokenUtil.getWeixinuserId(token);
        System.out.println(weixinuserId);
        return new ReturnString(0, "你已通过验证");
    }

    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     */
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getJsonObject(String url) throws IOException {
        URL serverUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        String result;
        try (InputStream inputStream = conn.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            result = buffer.toString();
        }
        return JSONObject.parseObject(result);
    }
}