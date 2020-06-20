package com.zhiliao.hotel.utils;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-19 15:28
 **/
public class AliyunOssUtil {

    public static String accessKeyId = "LTAI4FzJPcJeNwJCiUQnc8s5";
    public static String accessKeySecret = "tD2gnQKDriTCoJ6iNrrCppeMfrv6Qs";
    public static String bucketName = "hzlimgtest";
    // 阿里云外链
    public static String endpoint = "http://hzlimgtest.oss-cn-hangzhou.aliyuncs.com/";

//    @Value("${tools.aliyun.access-key}")
//    public static String accessKeyId;
//    @Value("${tools.aliyun.access-key-secret}")
//    public static String accessKeySecret;
//    @Value("${tools.aliyun.oss.bucket}")
//    public static String bucketName;
    // 阿里云外链
//    @Value("${tools.aliyun.oss.end-point}")
//    public static String endpoint;

    public static boolean FileUpload(File file, String fileName) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (file != null) {
            try {
                ossClient.putObject(bucketName, fileName, file);
                // 关闭OSSClient。
                ossClient.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
        //调用方法查询文件是否存在
        return FileExites(fileName);
    }

    /**
     * 判断文件是否存在
     */
    public static boolean FileExites(String fileName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果
        //为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(bucketName, fileName);
        if (found) {
            System.out.println("上传成功！");
        } else {
            System.out.println("上传失败！");
        }
        System.out.println(found);
        return found;
    }

}
