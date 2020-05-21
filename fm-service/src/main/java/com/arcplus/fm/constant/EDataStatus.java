package com.arcplus.fm.constant;

/**
 * @author jv_team
 * @date 2019/10/23 10:21
 */
public enum EDataStatus {
    N("正常"),
    D("删除");

    private String value;
    EDataStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
