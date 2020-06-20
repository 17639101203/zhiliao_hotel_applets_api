package com.zhiliao.hotel.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
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
    // 阿里云上传链接
    public static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com/";
    // 阿里云查看链接
    public static String visitendpoint = "https://hzlimgtest.oss-cn-hangzhou.aliyuncs.com/";

    //知了管家后端图片文件夹
    public static String zlgjhdFolderName = "zlgjhd";

    //知了管家前端端图片文件夹
    public static String zlgjqdFolderName = "zlgjqd";


    //上传图片
    public static boolean FileUpload(File file, String fileName) {

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        //判断是否需要创建文件夹
        if (StringUtils.isNoneBlank(zlgjhdFolderName)) {
            final String keySuffixWithSlash = zlgjhdFolderName;
            //判断文件夹是否存在，不存在则创建
            if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
                //创建文件夹
                ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
                //得到文件夹名
                OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
                String fileDir = object.getKey();
            }
            if (file != null) {
                try {
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType("image/jpg");
                    ossClient.putObject(bucketName, zlgjhdFolderName + "/" + fileName, file, objectMetadata);
                    // 关闭OSSClient。
                    ossClient.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            if (file != null) {
                try {
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType("image/jpg");
                    ossClient.putObject(bucketName, fileName, file, objectMetadata);
                    // 关闭OSSClient。
                    ossClient.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }
        //调用方法查询文件是否存在
        return FileExites(zlgjhdFolderName + "/" + fileName);
    }

    //上传图片
    public static boolean qdFileUpload(File file, String fileName) {

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        //判断是否需要创建文件夹
        if (StringUtils.isNoneBlank(zlgjqdFolderName)) {
            final String keySuffixWithSlash = zlgjqdFolderName;
            //判断文件夹是否存在，不存在则创建
            if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
                //创建文件夹
                ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
                //得到文件夹名
                OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
                String fileDir = object.getKey();
            }
            if (file != null) {
                try {
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType("image/jpg");
                    ossClient.putObject(bucketName, zlgjqdFolderName + "/" + fileName, file, objectMetadata);
                    // 关闭OSSClient。
                    ossClient.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            if (file != null) {
                try {
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType("image/jpg");
                    ossClient.putObject(bucketName, fileName, file, objectMetadata);
                    // 关闭OSSClient。
                    ossClient.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }
        //调用方法查询文件是否存在
        return FileExites(zlgjqdFolderName + "/" + fileName);
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
