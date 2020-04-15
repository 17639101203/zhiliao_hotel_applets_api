package com.zhiliao.hotel.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by fish on 2018/11/26.
 */
public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 删除文件
     * @param filePath
     */
    public static void deleteFile(String filePath, String fileName){
        File file = new File(filePath + fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 获取文件后缀（不带.）
     * @param file
     * @return
     */
    public static String getFileSuffix(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }
}
