package com.arcplus.fm.constant;


//'R',来自报修产生的工单；'S',来自定期维护产生的工单
public enum EFromBiz {
  R("来自报修产生的工单"),
  S("来自定期维护产生的工单");

  private String value;
  EFromBiz(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
