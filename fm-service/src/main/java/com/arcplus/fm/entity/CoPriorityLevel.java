package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;

import java.io.Serializable;
import java.util.Date;


/**
 * 共用-优先级别
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-20 14:20:07
 */
public class CoPriorityLevel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //优先级id
    private Integer priorityId;
	
	    //等级值
    private Integer levelValue;
	
	    //等级名称
    private String levelName;
	
	    //优先级别响应时间（分）
    private Integer responeTime;
	
	    //备注
    private String remark;
	
	    //创建时间
    private Date createAt;
	
	    //创建人
    private Integer createBy;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：优先级id
	 */
	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}
	/**
	 * 获取：优先级id
	 */
	public Integer getPriorityId() {
		return priorityId;
	}
	/**
	 * 设置：等级值
	 */
	public void setLevelValue(Integer levelValue) {
		this.levelValue = levelValue;
	}
	/**
	 * 获取：等级值
	 */
	public Integer getLevelValue() {
		return levelValue;
	}
	/**
	 * 设置：等级名称
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	/**
	 * 获取：等级名称
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * 设置：优先级别响应时间（分）
	 */
	public void setResponeTime(Integer responeTime) {
		this.responeTime = responeTime;
	}
	/**
	 * 获取：优先级别响应时间（分）
	 */
	public Integer getResponeTime() {
		return responeTime;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
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
	 * 设置：创建人
	 */
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getCreateBy() {
		return createBy;
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
