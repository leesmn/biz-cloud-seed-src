package com.arcplus.fm.entity.dto;

import lombok.Data;

@Data
public class WorkorderLiteDto {
  //报修单号
  private String repairCode;
  //处理组组长
  private Integer assignmentGroupAdmin;

  //处理组组长姓名
  private Integer assignmentAdminName;

  //优先级别，1-5（红,，橙，黄，绿，蓝）
  private Integer priorityLevel;

  //故障类型id
  private Integer failureTypeId;
}
