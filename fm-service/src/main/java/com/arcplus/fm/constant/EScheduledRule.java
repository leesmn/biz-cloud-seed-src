package com.arcplus.fm.constant;


//day，week，month，year
public enum EScheduledRule {
  O("不重复"),
  D("天"),
  W("周"),
  M("月"),
  Y("年");

  private String value;
  EScheduledRule(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
