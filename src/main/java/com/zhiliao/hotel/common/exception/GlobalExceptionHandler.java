package com.zhiliao.hotel.common.exception;

import com.zhiliao.hotel.common.ReturnString;
import io.netty.util.internal.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static java.lang.String.valueOf;

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
        logger.error("发生业务异常,原因是：{}", e.getErrorMessage());
        return new ReturnString(-1, e.getErrorMessage());
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
        logger.error("出错啦！原因是：", e);
        return new ReturnString(CommonExceptionEnum.INTERNAL_SERVER_ERROR.getErrorCode(), CommonExceptionEnum.INTERNAL_SERVER_ERROR.getErrorMessage());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ReturnString exceptionHandler(MethodArgumentNotValidException e) {

        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if (msg.equals(message)) {
            message = str[1] + ":" + message;
        }


        logger.error("发生业务异常,原因是：{}", message);
        return new ReturnString(-1, message);
    }
}
