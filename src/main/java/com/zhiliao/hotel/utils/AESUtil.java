package com.zhiliao.hotel.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    public static void main(String args[]) throws Exception {
        String content = "2020-04-17 16:35:00";
        //加密
        String encrypted = encrypt(content);
        //解密
        String decrypted = decrypt(encrypted);

        System.out.println("加密前：" + content);

        System.out.println("加密后：" + encrypted);

        System.out.println("解密后：" + decrypted);
    }

    private static String KEY = "zhiliao666888999";

    private static String IV = "zhiliao666888999";

    /**
     * 加密返回的数据转换成 String 类型
     *
     * @param content 明文
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) throws Exception {
        return parseByte2HexStr(AES_CBC_Encrypt(content.getBytes(), KEY.getBytes(), IV.getBytes()));
    }

    /**
     * 将解密返回的数据转换成 String 类型
     *
     * @param content Base64编码的密文
     * @return
     * @throws Exception
     */
    public static String decrypt(String content) throws Exception {
        return new String(AES_CBC_Decrypt(parseHexStr2Byte(content), KEY.getBytes(), IV.getBytes()));
    }

    private static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return null;
    }

    private static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv) {
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return null;
    }

    /**
     * 将byte数组转换成16进制String
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制String转换为byte数组
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }

        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }

        return result;
    }
}