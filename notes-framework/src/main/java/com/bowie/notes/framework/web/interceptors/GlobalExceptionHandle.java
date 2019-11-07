package com.bowie.notes.framework.web.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    /**
     * 拦截所有异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String businessInterfaceException(Exception e) {
        log.error(e.getMessage(), e);
        return String.format("系统异常,请稍后重试,错误信息:%s", e.getMessage());
    }

}
