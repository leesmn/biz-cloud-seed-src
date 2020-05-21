package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;

import java.io.Serializable;
import java.util.Date;


/**
 * 业务-保修处理过程
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
public class BoWorkorderProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    private Integer processId;
	
	    //报修编号
    private String woCode;
	
    //处理节点名称
    private String dealDescribe;

		//处理意见
		private String processRemark;

		//创建者
		private Integer createBy;

		//创建者姓名
		private String createName;
	
	    //创建时间
    private Date createAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * 获取：
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * 设置：报修编号
	 */
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	/**
	 * 获取：报修编号
	 */
	public String getWoCode() {
		return woCode;
	}
	/**
	 * 设置：处理意见
	 */
	public void setDealDescribe(String dealDescribe) {
		this.dealDescribe = dealDescribe;
	}
	/**
	 * 获取：处理意见
	 */
	public String getDealDescribe() {
		return dealDescribe;
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

	public String getProcessRemark() {
		return processRemark;
	}

	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
