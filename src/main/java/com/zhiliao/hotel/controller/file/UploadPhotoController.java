package com.zhiliao.hotel.controller.file;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.file.params.UploadPhotoParam;
import com.zhiliao.hotel.utils.AliyunOssUtil;
import com.zhiliao.hotel.utils.MultipartFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-20 17:33
 **/
@Api(tags = "上传图片接口_姬慧慧")
@RestController
@RequestMapping("uploadPhoto")
public class UploadPhotoController {

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "uploadPhoto", consumes = {"multipart/*"}, headers = "content-type=multipart/form-data")
    @PassToken
    public ReturnString uploadPhoto(
            UploadPhotoParam uploadPhotoParam,
            @RequestParam(value = "multipartFile") MultipartFile multipartFile) {

        String imgurl = "";

        try {
            File file = MultipartFileUtil.multipartFileToFile(multipartFile);
            String originalFilename = multipartFile.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String extention = originalFilename.substring(index - 1);
            String fileName = uploadPhotoParam.getSpecialName() + UUID.randomUUID().toString() + extention;
            //将文件上传到阿里云服务器
            boolean bool = AliyunOssUtil.qdFileUpload(file, fileName);
            file.delete();
            imgurl = AliyunOssUtil.visitendpoint + AliyunOssUtil.zlgjqdFolderName + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(imgurl);
        return new ReturnString(0, imgurl);

    }

}
