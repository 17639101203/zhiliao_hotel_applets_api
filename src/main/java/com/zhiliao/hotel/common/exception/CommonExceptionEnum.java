package com.zhiliao.hotel.common.exception;

/**
 * Created by xiegege on 2020/5/14.
 * 提示枚举类
 */
public enum CommonExceptionEnum {

    // 数据操作错误定义
    BODY_NOT_MATCH(400, "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!");

    /**
     * 错误码
     */
    private final int errorCode;

    /**
     * 错误描述
     */
    private final String errorMessage;

    CommonExceptionEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}