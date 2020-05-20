package com.zhiliao.hotel.common.exception;

import com.zhiliao.hotel.common.ReturnString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiegege
 * @date 2020/5/20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ReturnString bizExceptionHandler(HttpServletRequest request, BizException e) {
        logger.error("发生业务异常！原因是：{}", e.getErrorMessage());
        return new ReturnString(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * 处理其他异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnString exceptionHandler(HttpServletRequest request, Exception e) {
        logger.error("服务器内部错误！原因是：", e);
        return new ReturnString(CommonExceptionEnum.INTERNAL_SERVER_ERROR.getErrorCode(), CommonExceptionEnum.INTERNAL_SERVER_ERROR.getErrorMessage());
    }
}