package com.zhiliao.hotel.utils;

/**
 * Created by xiegege on 2019/7/4.
 */

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 环境类
 */
public class UploadFileContext {

    // 具体策略的对象
    private UploadFileStrategy uploadFileStrategy;

//    public UploadFileContext(UploadFileStrategy uploadFileStrategy) {
//        this.uploadFileStrategy = uploadFileStrategy;
//    }

    // 策略工厂
    public void factory(String fileSuffix) {
        if (fileSuffix.equalsIgnoreCase("JPG") || fileSuffix.equalsIgnoreCase("JPEG") || fileSuffix.equalsIgnoreCase("GIF")
                || fileSuffix.equalsIgnoreCase("PNG") || fileSuffix.equalsIgnoreCase("BMP") || fileSuffix.equalsIgnoreCase("PCX")
                || fileSuffix.equalsIgnoreCase("TGA") || fileSuffix.equalsIgnoreCase("PSD") || fileSuffix.equalsIgnoreCase("TIFF")) {
            uploadFileStrategy = new UploadImageStrategy();
        } else if (fileSuffix.equalsIgnoreCase("XLS") || fileSuffix.equalsIgnoreCase("XLSX")) {
            uploadFileStrategy = new UploadExcelStrategy();
        }
    }

    /**
     * 策略方法
     */
    public Map uploadFileStrategyMethod(MultipartFile multipartFile, String savePath, String backPath) {
        return uploadFileStrategy.uploadFileStrategyMethod(multipartFile, savePath, backPath);
    }
}