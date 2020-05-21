package com.arcplus.fm.config;

import com.arcplus.fm.common.ServiceException;
import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * feignClient调用异常，将服务的异常和http状态码解析
     *
     * @param exception
     * @param response
     * @return
     */
    @ExceptionHandler({FeignException.class})
    public Map<String, Object> feignException(FeignException exception, HttpServletResponse response) {
        int httpStatus = exception.status();
        if (httpStatus >= 500) {
            log.error("feignClient调用异常", exception);
        }

        Map<String, Object> data = new HashMap<>();

        String msg = exception.getMessage();

        if (!StringUtils.isEmpty(msg)) {
            int index = msg.indexOf("\n");
            if (index > 0) {
                String string = msg.substring(index);
                if (!StringUtils.isEmpty(string)) {
                    JSONObject json = JSONObject.parseObject(string.trim());
                    data.putAll(json.getInnerMap());
                }
            }
        }
        if (data.isEmpty()) {
            data.put("message", msg);
        }

        data.put("code", httpStatus + "");

        response.setStatus(httpStatus);

        return data;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequestException(IllegalArgumentException exception) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.BAD_REQUEST.value());
        data.put("message", exception.getMessage());

        return data;
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> inRuntimException(RuntimeException exception) {

        log.error(exception.toString(), exception);


        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        data.put("message", exception.toString());

        return data;
    }

    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> inServiceException(ServiceException exception) {

        log.error(exception.toString(), exception);


        Map<String, Object> data = new HashMap<>();
        data.put("code", exception.getErrorCode());
        data.put("message", exception.getMessage());

        return data;
    }


    @ExceptionHandler({CustomerArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> argException(CustomerArgumentException exception) {

        log.error(exception.toString(), exception);

        Map<String, Object> data = new HashMap<>();
        data.put("code", exception.getCode());
        data.put("message", exception.getMsg());

        return data;
    }

    public static class CustomerArgumentException extends RuntimeException {

        private int code;  //异常对应的返回码
        private String msg;  //异常对应的描述信息

        public CustomerArgumentException() {
            super();
        }

        public CustomerArgumentException(String message) {
            super(message);
            code = 200;
        }

        public CustomerArgumentException(int code, String msg) {
            super();
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
