package com.zhiliao.hotel.utils;

import org.springframework.web.multipart.MultipartFile;

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

}
