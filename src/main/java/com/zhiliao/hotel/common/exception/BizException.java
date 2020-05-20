package com.zhiliao.hotel.common.exception;

/**
 * Created by xiegege on 2020/5/14.
 * 自定义一个异常类，用于处理我们发生的业务异常
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected int errorCode;
    /**
     * 错误信息
     */
    protected String errorMessage;

    public BizException() {
        super();
    }

    public BizException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BizException(int errorCode, String errorMessage) {
        super(String.valueOf(errorCode));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(int errorCode, String errorMessage, Throwable cause) {
        super(String.valueOf(errorCode), cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}