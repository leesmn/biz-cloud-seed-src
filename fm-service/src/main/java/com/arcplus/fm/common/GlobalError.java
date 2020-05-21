package com.arcplus.fm.common;

/**
 * @author jv_team
 * @date 2019/10/30 18:37
 */
public enum GlobalError {
    ERROR_HAS_DATA(1001),
    ERROR_INVALID_REQUEST(1002),
    ERROR_TYPE_IVALID(1003),
    ERROR_JOBHANDLER_NO_EXIST(1004),
    ERROR_HAS_CHILD(1005),
    ERROR_KEY_NULL(1006),
    ERROR_INVALID_COLUMN(1007),
    ERROR_DATA_NO_EXIST(1008),
    ERROR_NO_DONE(1009);
    private Integer code;
    GlobalError(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
