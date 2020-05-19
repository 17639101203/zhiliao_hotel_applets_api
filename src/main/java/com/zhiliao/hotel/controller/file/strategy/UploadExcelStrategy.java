package com.zhiliao.hotel.controller.file.strategy;

/**
 * Created by xiegege on 2019/7/4.
 */

import com.zhiliao.hotel.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 表格上传策略类
 */
public class UploadExcelStrategy implements UploadFileStrategy {

    @Override
    public Map uploadFileStrategyMethod(MultipartFile multipartFile, String savePath, String backPath) {
        try {
            Map<String, Object> map = new HashMap<>(1);
            String fileName = multipartFile.getOriginalFilename();
            // 删除现有的文件
            FileUtil.deleteFile(savePath, fileName);
            // 上传文件
            FileUtil.uploadFile(multipartFile.getBytes(), savePath, fileName);
            Workbook workbook = null;
            // 读取excel
            File file = new File(savePath + fileName);
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(file);
            String suffix = FileUtil.getFileSuffix(multipartFile);
            if ("xls".equals(suffix)) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else if ("xlsx".equals(suffix)) {
                workbook = new XSSFWorkbook(fileInputStream);
            }
            map.put("workbook", workbook);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}