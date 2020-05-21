package com.arcplus.fm.constant;

public enum EDealWith {
    O("未处理"),
    D("直接处理"),
    W("工单处理");

    private String value;
    EDealWith(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }
}
