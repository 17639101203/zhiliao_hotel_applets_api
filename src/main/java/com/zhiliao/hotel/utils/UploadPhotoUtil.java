package com.zhiliao.hotel.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-18 16:30
 **/
public class UploadPhotoUtil {

    public static String uploadPhotoUtil(MultipartFile multipartFile) {


        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1);
        String fileName = UUID.randomUUID().toString() + extention;
        //将文件上传到七牛云服务器
        try {
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(), fileName);
            String imgurl = QiniuUtils.domain + fileName;
            return imgurl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uploadPhotoUtil2(MultipartFile multipartFile) {

        String imgurl = "";
        try {
            File file = MultipartFileUtil.multipartFileToFile(multipartFile);
            String originalFilename = multipartFile.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String extention = originalFilename.substring(index - 1);
            String fileName = UUID.randomUUID().toString() + extention;
            //将文件上传到阿里云服务器
            boolean bool = AliyunOssUtil.FileUpload(file, fileName);
            file.delete();
            imgurl = AliyunOssUtil.visitendpoint + AliyunOssUtil.zlgjhdFolderName + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgurl;
    }

}
