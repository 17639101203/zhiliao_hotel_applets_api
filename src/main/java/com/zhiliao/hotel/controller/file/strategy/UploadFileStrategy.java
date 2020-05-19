package com.zhiliao.hotel.controller.file.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by xiegege on 2019/7/4.
 */
public interface UploadFileStrategy {

    /**
     * 文件相关的策略方法
     * @param multipartFile
     * @param savePath
     * @param backPath
     * @return
     */
    Map uploadFileStrategyMethod(MultipartFile multipartFile, String savePath, String backPath);
}