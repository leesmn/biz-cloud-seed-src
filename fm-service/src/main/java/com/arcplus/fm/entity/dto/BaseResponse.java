package com.arcplus.fm.entity.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 响应的返回结果集
 */
@Data
@Builder
public class BaseResponse {
    public long totalCount = 0;
    public long pageNo = 0;
    //    public Long ElapseTime;
    public boolean IsSuccess;
    public String Message;
    public Object data;
    public Object Exception;
}
