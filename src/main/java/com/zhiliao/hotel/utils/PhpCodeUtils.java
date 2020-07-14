package com.zhiliao.hotel.utils;

import jodd.util.URLDecoder;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;

import java.util.Base64;

/**
 * @Classname PhpCodeUtils
 * @Description TODO
 * @Date 2020/7/13 17:13
 * @Created by FX80
 */
public class PhpCodeUtils {

    private static String base64Table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-=+";

    private static String base64Key = "D8vp531";

    /**
     * BASE64解密
     * @throws Exception
     */
    public static String decodeStr(String txt) throws Exception {
//        char ch = txt.charAt(0);
//        int nh = base64Table.indexOf(ch);
//        String mdKey = DigestUtils.md5Hex(base64Key + ch);
//        mdKey = mdKey.substring(nh % 8, nh % 8 + 8);
//        txt = txt.substring(1);
//        String tmp = "";
//        int k = 0;
//        int j = 0;
//        int ord = 0;
//        for (int i = 0; i < txt.length(); i ++  ){
//            k = k==mdKey.length()?0:k;
//            ord = Integer.valueOf(mdKey.charAt(k++));
//            j = base64Table.indexOf(txt.charAt(i)) - nh - ord;
//            while (j < 0 ) {
//                j+=64;
//            }
//            tmp = tmp + base64Table.charAt(j);
//        }

        String decode = URLDecoder.decode(txt, "GBK");// GBK解码
        //byte[] byteArray = decryptBASE64(decode);
        //System.out.println("sun.misc.BASE64 解密后：" + new String(byteArray));

        //Base64 解密
        byte[] decoded = Base64.getDecoder().decode(decode);
        String decodeStr = new String(decoded);
        decodeStr = decodeStr.replaceAll(base64Key,"");

        return decodeStr;
    }
}
