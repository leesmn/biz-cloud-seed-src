package com.arcplus.fm.constant;

/*
 工单的处理流程状态节点
 */
public enum EWoStatus {
  CR("创建并分配班组"),
  AD("班组拒绝"),
  AM("班组接单并指派维修工"),
  MR("维修工已读"),
  MA("维修工接单"),
  MD("维修工拒绝"),
  MB("维修工处理前"),
  MO("维修工处理完成"),
  AC("班组确认完成"),
  AR("班组驳回"),
  CL("已结束");

  private String value;
  EWoStatus(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
