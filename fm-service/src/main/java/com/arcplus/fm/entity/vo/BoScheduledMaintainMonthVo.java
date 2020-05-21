package com.arcplus.fm.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class BoScheduledMaintainMonthVo {
  //主键id
  private Integer scheduledId;

  private String groupUuid;
  //定期维护内容
  private String maintainContent;

  //维护描述
  private String maintainDescribe;
  //定期开始时间
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @JsonFormat( pattern = "yyyy-MM-dd")
  private Date maintainStartAt;

  //优先级别，0-4（黑，绿，蓝，黄，红）递增
  private Integer priorityLevel;


  public Integer getScheduledId() {
    return scheduledId;
  }

  public void setScheduledId(Integer scheduledId) {
    this.scheduledId = scheduledId;
  }

  public String getGroupUuid() {
    return groupUuid;
  }

  public void setGroupUuid(String groupUuid) {
    this.groupUuid = groupUuid;
  }

  public String getMaintainContent() {
    return maintainContent;
  }

  public void setMaintainContent(String maintainContent) {
    this.maintainContent = maintainContent;
  }

  public String getMaintainDescribe() {
    return maintainDescribe;
  }

  public void setMaintainDescribe(String maintainDescribe) {
    this.maintainDescribe = maintainDescribe;
  }

  public Date getMaintainStartAt() {
    return maintainStartAt;
  }

  public void setMaintainStartAt(Date maintainStartAt) {
    this.maintainStartAt = maintainStartAt;
  }

  public Integer getPriorityLevel() {
    return priorityLevel;
  }

  public void setPriorityLevel(Integer priorityLevel) {
    this.priorityLevel = priorityLevel;
  }
}
