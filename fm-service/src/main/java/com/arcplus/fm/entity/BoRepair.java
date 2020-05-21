package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EDealWith;
import java.io.Serializable;
import java.util.Date;


/**
 * 业务-保修
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-23 09:37:18
 */
public class BoRepair implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    private Integer repairId;
	
	    //报修编号
    private String repairCode;
	
	    //业主姓名
    private String ownerName;
	
	    //联系电话
    private String contactPhone;
	
	    //故障类型id
    private Integer failureTypeId;
	
	    //事发楼栋id
    private Integer incidentBuildingId;
	
	    //事发楼层id
    private Integer incidentFloorId;
	
	    //楼宇名称
    private String incidentBuildingName;
	
	    //楼层名称
    private String incidentFloorName;
	
	    //保修描述
    private String repairDescribe;
	
	    //分公司id
    private Integer branchCompanyId;
	
	    //'W',待处理；'P',处理中；'F',已完成；'I',已忽略
    private EDealStatus dealStatus;
	
	    //'/'未知默认,'D'直接处理,'W'创建工单
    private EDealWith dealWith;
	
	    //创建者
    private Integer createBy;
	
	    //创建时间
    private Date createAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	
	    //现场照片
    private String scenePic;
	
	    //没有工单的原因
    private String unwoReason;
	
	    //给用户反馈信息
    private String feedbackUserInfo;
	

	/**
	 * 设置：
	 */
	public void setRepairId(Integer repairId) {
		this.repairId = repairId;
	}
	/**
	 * 获取：
	 */
	public Integer getRepairId() {
		return repairId;
	}
	/**
	 * 设置：报修编号
	 */
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	/**
	 * 获取：报修编号
	 */
	public String getRepairCode() {
		return repairCode;
	}
	/**
	 * 设置：业主姓名
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	/**
	 * 获取：业主姓名
	 */
	public String getOwnerName() {
		return ownerName;
	}
	/**
	 * 设置：联系电话
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	/**
	 * 设置：故障类型id
	 */
	public void setFailureTypeId(Integer failureTypeId) {
		this.failureTypeId = failureTypeId;
	}
	/**
	 * 获取：故障类型id
	 */
	public Integer getFailureTypeId() {
		return failureTypeId;
	}
	/**
	 * 设置：事发楼栋id
	 */
	public void setIncidentBuildingId(Integer incidentBuildingId) {
		this.incidentBuildingId = incidentBuildingId;
	}
	/**
	 * 获取：事发楼栋id
	 */
	public Integer getIncidentBuildingId() {
		return incidentBuildingId;
	}
	/**
	 * 设置：事发楼层id
	 */
	public void setIncidentFloorId(Integer incidentFloorId) {
		this.incidentFloorId = incidentFloorId;
	}
	/**
	 * 获取：事发楼层id
	 */
	public Integer getIncidentFloorId() {
		return incidentFloorId;
	}
	/**
	 * 设置：楼宇名称
	 */
	public void setIncidentBuildingName(String incidentBuildingName) {
		this.incidentBuildingName = incidentBuildingName;
	}
	/**
	 * 获取：楼宇名称
	 */
	public String getIncidentBuildingName() {
		return incidentBuildingName;
	}
	/**
	 * 设置：楼层名称
	 */
	public void setIncidentFloorName(String incidentFloorName) {
		this.incidentFloorName = incidentFloorName;
	}
	/**
	 * 获取：楼层名称
	 */
	public String getIncidentFloorName() {
		return incidentFloorName;
	}
	/**
	 * 设置：保修描述
	 */
	public void setRepairDescribe(String repairDescribe) {
		this.repairDescribe = repairDescribe;
	}
	/**
	 * 获取：保修描述
	 */
	public String getRepairDescribe() {
		return repairDescribe;
	}
	/**
	 * 设置：分公司id
	 */
	public void setBranchCompanyId(Integer branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
	/**
	 * 获取：分公司id
	 */
	public Integer getBranchCompanyId() {
		return branchCompanyId;
	}
	/**
	 * 设置：'W',待处理；'P',处理中；'F',已完成；'I',已忽略
	 */
	public void setDealStatus(EDealStatus dealStatus) {
		this.dealStatus = dealStatus;
	}
	/**
	 * 获取：'W',待处理；'P',处理中；'F',已完成；'I',已忽略
	 */
	public EDealStatus getDealStatus() {
		return dealStatus;
	}
	/**
	 * 设置：'O'未未处理默认,'D'直接处理,'W'创建工单
	 */
	public void setDealWith(EDealWith dealWith) {
		this.dealWith = dealWith;
	}
	/**
	 * 获取：'O'未未处理默认,'D'直接处理,'W'创建工单
	 */
	public EDealWith getDealWith() {
		return dealWith;
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
	/**
	 * 设置：现场照片
	 */
	public void setScenePic(String scenePic) {
		this.scenePic = scenePic;
	}
	/**
	 * 获取：现场照片
	 */
	public String getScenePic() {
		return scenePic;
	}
	/**
	 * 设置：没有工单的原因
	 */
	public void setUnwoReason(String unwoReason) {
		this.unwoReason = unwoReason;
	}
	/**
	 * 获取：没有工单的原因
	 */
	public String getUnwoReason() {
		return unwoReason;
	}
	/**
	 * 设置：给用户反馈信息
	 */
	public void setFeedbackUserInfo(String feedbackUserInfo) {
		this.feedbackUserInfo = feedbackUserInfo;
	}
	/**
	 * 获取：给用户反馈信息
	 */
	public String getFeedbackUserInfo() {
		return feedbackUserInfo;
	}
}
