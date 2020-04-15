package com.zhiliao.hotel.utils;

/**
 * Created by xiegege on 2019/7/4.
 */
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 图片上传策略类
 */

@Component
public class UploadImageStrategy implements UploadFileStrategy {

    @Override
    public Map uploadFileStrategyMethod(MultipartFile multipartFile, String savePath, String backPath) {
        try {
            Map<String, Object> dataMap = new HashMap<>();
            // 获取文件后缀（带.）
            String fileSuffix = getFileSuffix(multipartFile);
            // 产生的文件名
            String uuId = UUID.randomUUID() + "";
            String fileName = uuId + fileSuffix;
            // 现网
            FileUtil.uploadFile(multipartFile.getBytes(), savePath, fileName);
            // 返回的文件网络路径
            String filePathBase = backPath + fileName;
            dataMap.put("filePathBase", filePathBase);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件后缀（带.）
     * @param file
     * @return
     */
    private static String getFileSuffix(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }
}