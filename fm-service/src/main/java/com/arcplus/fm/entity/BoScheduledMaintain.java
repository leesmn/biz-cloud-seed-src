package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EScheduledRule;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 业务-定期维护
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
public class BoScheduledMaintain implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键id
    private Integer scheduledId;

    //一组uuid
    private String groupUuid;
	
	    //设备code
    private String facilityCode;
	
	    //维护对象设备或系统
    private String facilityName;
	
	    //定期维护内容
    private String maintainContent;
	
	    //维护描述
    private String maintainDescribe;
	
	    //定期开始时间
	  @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date maintainStartAt;
	
	    //执行人
    private Integer assignment;
	
	    //执行组
    private Integer assignmentGroup;
	
	    //执行人联系电话
    private String contactPhone;
	
	    //优先级别，1-4（绿，蓝，黄，红）递增
    private Integer priorityLevel;
	
	    //任务计划执行规则；day，week，month，year
    private EScheduledRule scheduledRule;
	
	    //循环中间间隔（单位以loop_rule为准）
    private Integer scheduledInterval;
	
	    //定期结束时间
    private Date scheduledEndAt;

	 //'W',待处理；'P',处理中；'F',已完成；'I',已忽略
	 private EDealStatus dealStatus;

	    //创建者
    private Integer createBy;
	
	    //创建时间
    private Date createAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：主键id
	 */
	public void setScheduledId(Integer scheduledId) {
		this.scheduledId = scheduledId;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getScheduledId() {
		return scheduledId;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	/**
	 * 设置：设备code
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * 获取：设备code
	 */
	public String getFacilityCode() {
		return facilityCode;
	}
	/**
	 * 设置：维护对象设备或系统
	 */
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	/**
	 * 获取：维护对象设备或系统
	 */
	public String getFacilityName() {
		return facilityName;
	}
	/**
	 * 设置：定期维护内容
	 */
	public void setMaintainContent(String maintainContent) {
		this.maintainContent = maintainContent;
	}
	/**
	 * 获取：定期维护内容
	 */
	public String getMaintainContent() {
		return maintainContent;
	}
	/**
	 * 设置：维护描述
	 */
	public void setMaintainDescribe(String maintainDescribe) {
		this.maintainDescribe = maintainDescribe;
	}
	/**
	 * 获取：维护描述
	 */
	public String getMaintainDescribe() {
		return maintainDescribe;
	}
	/**
	 * 设置：定期开始时间
	 */
	public void setMaintainStartAt(Date maintainStartAt) {
		this.maintainStartAt = maintainStartAt;
	}
	/**
	 * 获取：定期开始时间
	 */
	public Date getMaintainStartAt() {
		return maintainStartAt;
	}
	/**
	 * 设置：执行人
	 */
	public void setAssignment(Integer assignment) {
		this.assignment = assignment;
	}
	/**
	 * 获取：执行人
	 */
	public Integer getAssignment() {
		return assignment;
	}
	/**
	 * 设置：执行组
	 */
	public void setAssignmentGroup(Integer assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	/**
	 * 获取：执行组
	 */
	public Integer getAssignmentGroup() {
		return assignmentGroup;
	}
	/**
	 * 设置：执行人联系电话
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/**
	 * 获取：执行人联系电话
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	/**
	 * 设置：优先级别，1-4（绿，蓝，黄，红）递增
	 */
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	/**
	 * 获取：优先级别，1-4（绿，蓝，黄，红）递增
	 */
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	/**
	 * 设置：任务计划执行规则；day，week，month，year
	 */
	public void setScheduledRule(EScheduledRule scheduledRule) {
		this.scheduledRule = scheduledRule;
	}
	/**
	 * 获取：任务计划执行规则；day，week，month，year
	 */
	public EScheduledRule getScheduledRule() {
		return scheduledRule;
	}
	/**
	 * 设置：循环中间间隔（单位以loop_rule为准）
	 */
	public void setScheduledInterval(Integer scheduledInterval) {
		this.scheduledInterval = scheduledInterval;
	}
	/**
	 * 获取：循环中间间隔（单位以loop_rule为准）
	 */
	public Integer getScheduledInterval() {
		return scheduledInterval;
	}
	/**
	 * 设置：定期结束时间
	 */
	public void setScheduledEndAt(Date scheduledEndAt) {
		this.scheduledEndAt = scheduledEndAt;
	}
	/**
	 * 获取：定期结束时间
	 */
	public Date getScheduledEndAt() {
		return scheduledEndAt;
	}

	public EDealStatus getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(EDealStatus dealStatus) {
		this.dealStatus = dealStatus;
	}

	/**
	 * 设置：创建者
	 */
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者
	 */
	public Integer getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateAt() {
		return createAt;
	}
	/**
	 * 设置：N,正常；D,删除
	 */
	public void setDataStatus(EDataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	/**
	 * 获取：N,正常；D,删除
	 */
	public EDataStatus getDataStatus() {
		return dataStatus;
	}
}
