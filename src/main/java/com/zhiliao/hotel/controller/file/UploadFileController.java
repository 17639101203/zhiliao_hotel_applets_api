package com.zhiliao.hotel.controller.file;

import com.zhiliao.hotel.common.NoLoginRequiredToken;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.file.strategy.UploadFileContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zyj on 2020/04/11.
 */

@Api(tags = "文件相关接口_章英杰")
@RestController
@RequestMapping("uploadFile")
@Slf4j
public class UploadFileController {

    @Value("${project.save.path}")
    private String savePath;
    @Value("${project.back.path}")
    private String backPath;


    /**
     * 传入文件，返回文件路径 mapkey值为：filePathBase
     */
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "uploadFile", consumes = {"multipart/*"}, headers = "content-type=multipart/form-data")
//    @PassToken
    @NoLoginRequiredToken
    public ReturnString<List<String>> uploadFile(MultipartFile[] multipartFiles) {  //OrderShowParam  token
        try {
            List<String> pathList = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                if (multipartFile == null) {
                    return new ReturnString<>(-1, "未接收到文件信息,请重新上传！");
                }
                UploadFileContext context = new UploadFileContext();
                // 获取文件后缀（不带.）
                String fileSuffix = getFileSuffix(multipartFile);
                log.info("文件后缀：" + fileSuffix);
                context.factory(fileSuffix);
                Map dataMap = context.uploadFileStrategyMethod(multipartFile, savePath, backPath);
                log.info("文件map：" + dataMap);
                // 判断是否成功返回文件路径
                if (dataMap == null) {
                    return new ReturnString<>(-1, "获取文件路径信息出错！");
                }
                pathList.add((String) dataMap.get("filePathBase"));
            }
            return new ReturnString<>(pathList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>(-1, "获取出错");

        }
    }

    private static String getFileSuffix(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }
}