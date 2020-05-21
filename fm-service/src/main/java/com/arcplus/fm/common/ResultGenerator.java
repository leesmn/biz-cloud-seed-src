package com.arcplus.fm.common;

import com.arcplus.fm.entity.dto.BaseResponse;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "ok";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message,ResultCode resultCode) {
        return new Result()
                .setCode(resultCode)
                .setMessage(message);
    }

    public static Result genSuccessPageResult(PageInfo page) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("list",page.getList());
        resultMap.put("total",page.getTotal());
        resultMap.put("page",page.getPageNum());
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(resultMap);
    }

    public static BaseResponse genSucessPageResult(PageInfo page){
        return BaseResponse.builder()
                .totalCount(page.getTotal())
                .pageNo(page.getPageNum())
                .IsSuccess(true)
                .Message("success")
                .data(page.getList())
                .build();
    }
}
