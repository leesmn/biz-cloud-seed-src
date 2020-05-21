package com.arcplus.fm.common;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    private Integer errorCode;


    public ServiceException(Integer code,String message) {
        super(message);
        this.errorCode = code;

    }

    public ServiceException(Integer code,String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;

    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
