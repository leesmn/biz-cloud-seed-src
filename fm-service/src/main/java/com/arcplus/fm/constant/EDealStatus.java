package com.arcplus.fm.constant;


//'W',待处理；'P',处理中；'F',已完成；'I',已忽略,   //'O',已关闭
public enum EDealStatus {
  W("待处理"),
  P("处理中"),
  F("已处理"),
  O("已关闭"),
  I("已忽略");

  private String value;
  EDealStatus(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
