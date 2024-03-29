package com.zhiliao.hotel.controller.wxuser;

import com.alibaba.fastjson.JSONObject;
import com.zhiliao.hotel.common.NoLoginRequiredToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.wxuser.params.WxuserLoginParam;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.service.ZlWxuserService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiegege
 * @date 2019/10/14
 */

@Api(tags = "微信用户接口_谢辉益")
@RestController
@RequestMapping("wxuser")
public class ZlWxuserController {

    private static final Logger logger = LoggerFactory.getLogger(ZlWxuserController.class);

    @Value("${weChat.appid}")
    private String APP_ID;
    @Value("${weChat.secret}")
    private String SECRET;

    private final ZlWxuserService zlWxuserService;
    private final RedisCommonUtil redisCommonUtil;

    @Autowired
    public ZlWxuserController(ZlWxuserService zlWxuserService, RedisCommonUtil redisCommonUtil) {
        this.zlWxuserService = zlWxuserService;
        this.redisCommonUtil = redisCommonUtil;
    }

    @ApiOperation(value = "微信用户登录")
    @NoLoginRequiredToken
    @PostMapping("wxuserLogin")
    public ReturnString wxuserLogin(@RequestBody WxuserLoginParam wxuserLoginParam) {
        try {
            logger.info("开始请求->参数->code：" + wxuserLoginParam.getCode() + "|加密秘钥：" + wxuserLoginParam.getEncryptedData() + "|偏移量：" + wxuserLoginParam.getIv());
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + wxuserLoginParam.getCode() + "&grant_type=authorization_code";
            JSONObject res = getJsonObject(url);
            logger.info("res: " + res);
            if (res == null) {
                return new ReturnString("解析失败!");
            }

            if (res.get("errcode") != null) {
                return new ReturnString("解析失败! ");
            }
            logger.info("res: " + res.get("errcode"));
            String openid = res.getString("openid");
            // 通过openid查找用户信息
            ZlWxuser wxuser = zlWxuserService.findWxuserByWxOpenId(openid);
            if (wxuser == null) {
                // 用户不存在，新增注册用户
                JSONObject res1 = getUserInfo(wxuserLoginParam.getEncryptedData(), String.valueOf(res.get("session_key")), wxuserLoginParam.getIv());
                if (res1 == null) {
                    return new ReturnString("解析失败! res1 session_key");
                }
                wxuser = new ZlWxuser();
                wxuser.setWxopenid(openid);
                wxuser.setWxunionid(res1.getString("unionId"));
                wxuser.setNickname(res1.getString("nickName"));
                wxuser.setHeadimgurl(res1.getString("avatarUrl"));
                wxuser.setSex(res1.getByte("gender"));
                // 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
                wxuser.setComeformid(1);
                // 删除状态:0正常;1删除
                wxuser.setIsdelete(false);
                wxuser.setCreatedate(DateUtils.javaToPhpNowDateTime());
                wxuser.setUpdatedate(DateUtils.javaToPhpNowDateTime());
                zlWxuserService.addWxuser(wxuser);
                ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
                zlWxuserdetail.setUserid(wxuser.getUserid());
                zlWxuserdetail.setRealname(wxuser.getNickname());
                zlWxuserService.addWxuserdetail(zlWxuserdetail);
            } else {
                // 用户存在，更新用户信息
                JSONObject res1 = getUserInfo(wxuserLoginParam.getEncryptedData(), String.valueOf(res.get("session_key")), wxuserLoginParam.getIv());
                if (res1 == null) {
                    return new ReturnString("解析失败! res1 session_key");
                }
                wxuser.setNickname(res1.getString("nickName"));
                wxuser.setHeadimgurl(res1.getString("avatarUrl"));
                wxuser.setSex(res1.getByte("gender"));
                wxuser.setComeformid(1);// 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
                wxuser.setUpdatedate(DateUtils.javaToPhpNowDateTime());
                zlWxuserService.updateWxuser(wxuser);
                if (!wxuser.getIsinfocompleted()) {
                    ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
                    zlWxuserdetail.setUserid(wxuser.getUserid());
                    zlWxuserdetail.setRealname(wxuser.getNickname());
                    zlWxuserService.updateWxuserdetail(zlWxuserdetail);
                }
            }
            return returnUserInfoData(wxuser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("解析失败!");
        }
    }

    private ReturnString returnUserInfoData(ZlWxuser wxuser) {
        Map<String, Object> dataMap = new HashMap<>();
        //获取登录Token
        String token = TokenUtil.getToken(wxuser);
        //获取刷新Token
        String flashToken = TokenUtil.getFlashToken(wxuser);
        dataMap.put("token", token);
        dataMap.put("flashToken", flashToken);
        // 头像
        dataMap.put("headImgUrl", wxuser.getHeadimgurl());
        // 微信昵称
        dataMap.put("nickName", wxuser.getNickname());
        // 登录成功设置token过期时间
        redisCommonUtil.setCache(wxuser.getWxopenid(), token, RedisKeyConstant.USERTOKENTIME);
        redisCommonUtil.setCache(wxuser.getWxopenid() + "flash", flashToken, RedisKeyConstant.USERFLASHTIME);
        return new ReturnString(dataMap);
    }

    @ApiOperation(value = "测试")
    @ApiImplicitParams({

    })
    @UserLoginToken
    @PostMapping("test")
    public ReturnString test(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        System.out.println(userId);
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
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
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
