package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EFromBiz;
import com.arcplus.fm.constant.EWoStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 业务-设备资产
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-28 14:37:04
 */
public class BoWorkorder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //工单id
    private Integer woId;
	
	    //'R',来自报修产生的工单；'S',来自定期维护产生的工单
    private EFromBiz fromBiz;
	
	    //工单有可能来自保修，有个能来自定期维护，这里分别来自两张表的主键
    private Integer formBizId;

    //报修单号
    private String repairCode;
	
	    //工单编号
    private String woCode;
	
	    //设备编码
    private String facilityCode;
	
	    //设备名称
    private String facilityName;
	
	    //处理人
    private Integer assignment;

	 //执行人姓名
	 private String assignmentName;
	
	    //处理组
    private Integer assignmentGroup;
	
	    //处理组组长
    private Integer assignmentGroupAdmin;

	 //处理组组长姓名
	 private String assignmentAdminName;
	
	    //优先级别，1-5（红,，橙，黄，绿，蓝）
    private Integer priorityLevel;

		/*
			CR("创建并分配班组"),
			AD("班组拒绝"),
			AM("班组接单并指派维修工"),
			MA("维修工接单"),
			MD("维修工拒绝"),
			MB("维修工处理前"),
			MO("维修工处理完成"),
			AC("班组确认完成"),
			AR("班组驳回");
	*/
    private EWoStatus dealStatus;

	 //维修工已读；0，未读；1，已读
	 private Integer workerRead;
	
	    //创建者
    private Integer createBy;
	
	    //创建时间
    private Date createAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	
	    //班组长反馈意见
    private String groupAdminDescribe;
	
	    //维修人员处理前，描述
    private String preDealDescribe;
	
	    //维修人员处理后，描述
    private String sufDealDescribe;
	
	    //备注
    private String remark;
	
	    //材料费
    private BigDecimal materialCost;
	
	    //人工费
    private BigDecimal laborCost;
	
	    //总费用
    private BigDecimal totalCost;
	
	    //业主签字
    private String signPicture;
	
	    //故障类型id
    private Integer failureTypeId;

    /*
    回访结果
     */
    private String visitResult;

    /*
    其他意见
     */
    private String otherComment;
	

	/**
	 * 设置：工单id
	 */
	public void setWoId(Integer woId) {
		this.woId = woId;
	}
	/**
	 * 获取：工单id
	 */
	public Integer getWoId() {
		return woId;
	}
	/**
	 * 设置：'R',来自报修产生的工单；'S',来自定期维护产生的工单
	 */
	public void setFromBiz(EFromBiz fromBiz) {
		this.fromBiz = fromBiz;
	}
	/**
	 * 获取：'R',来自报修产生的工单；'S',来自定期维护产生的工单
	 */
	public EFromBiz getFromBiz() {
		return fromBiz;
	}
	/**
	 * 设置：工单有可能来自保修，有个能来自定期维护，这里分别来自两张表的主键
	 */
	public void setFormBizId(Integer formBizId) {
		this.formBizId = formBizId;
	}
	/**
	 * 获取：工单有可能来自保修，有个能来自定期维护，这里分别来自两张表的主键
	 */
	public Integer getFormBizId() {
		return formBizId;
	}

	public String getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	/**
	 * 设置：工单编号
	 */
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	/**
	 * 获取：工单编号
	 */
	public String getWoCode() {
		return woCode;
	}
	/**
	 * 设置：设备编码
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * 获取：设备编码
	 */
	public String getFacilityCode() {
		return facilityCode;
	}
	/**
	 * 设置：设备名称
	 */
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getFacilityName() {
		return facilityName;
	}
	/**
	 * 设置：处理人
	 */
	public void setAssignment(Integer assignment) {
		this.assignment = assignment;
	}
	/**
	 * 获取：处理人
	 */
	public Integer getAssignment() {
		return assignment;
	}
	/**
	 * 设置：处理组
	 */
	public void setAssignmentGroup(Integer assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	/**
	 * 获取：处理组
	 */
	public Integer getAssignmentGroup() {
		return assignmentGroup;
	}
	/**
	 * 设置：处理组组长
	 */
	public void setAssignmentGroupAdmin(Integer assignmentGroupAdmin) {
		this.assignmentGroupAdmin = assignmentGroupAdmin;
	}
	/**
	 * 获取：处理组组长
	 */
	public Integer getAssignmentGroupAdmin() {
		return assignmentGroupAdmin;
	}
	/**
	 * 设置：优先级别，1-5（红,，橙，黄，绿，蓝）
	 */
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	/**
	 * 获取：优先级别，1-5（红,，橙，黄，绿，蓝）
	 */
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	/**
	 * 设置：'W',待分派；'P',处理中；'F',已完成；'O',已关闭
	 */
	public void setDealStatus(EWoStatus dealStatus) {
		this.dealStatus = dealStatus;
	}
	/**
	 * 获取：'W',待分派；'P',处理中；'F',已完成；'O',已关闭
	 */
	public EWoStatus getDealStatus() {
		return dealStatus;
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
	 * 设置：班组长反馈意见
	 */
	public void setGroupAdminDescribe(String groupAdminDescribe) {
		this.groupAdminDescribe = groupAdminDescribe;
	}
	/**
	 * 获取：班组长反馈意见
	 */
	public String getGroupAdminDescribe() {
		return groupAdminDescribe;
	}
	/**
	 * 设置：维修人员处理前，描述
	 */
	public void setPreDealDescribe(String preDealDescribe) {
		this.preDealDescribe = preDealDescribe;
	}
	/**
	 * 获取：维修人员处理前，描述
	 */
	public String getPreDealDescribe() {
		return preDealDescribe;
	}
	/**
	 * 设置：维修人员处理后，描述
	 */
	public void setSufDealDescribe(String sufDealDescribe) {
		this.sufDealDescribe = sufDealDescribe;
	}
	/**
	 * 获取：维修人员处理后，描述
	 */
	public String getSufDealDescribe() {
		return sufDealDescribe;
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
	 * 设置：材料费
	 */
	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}
	/**
	 * 获取：材料费
	 */
	public BigDecimal getMaterialCost() {
		return materialCost;
	}
	/**
	 * 设置：人工费
	 */
	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}
	/**
	 * 获取：人工费
	 */
	public BigDecimal getLaborCost() {
		return laborCost;
	}
	/**
	 * 设置：总费用
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	/**
	 * 获取：总费用
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	/**
	 * 设置：业主签字
	 */
	public void setSignPicture(String signPicture) {
		this.signPicture = signPicture;
	}
	/**
	 * 获取：业主签字
	 */
	public String getSignPicture() {
		return signPicture;
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

	public String getVisitResult() {
		return visitResult;
	}

	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}

	public String getOtherComment() {
		return otherComment;
	}

	public void setOtherComment(String otherComment) {
		this.otherComment = otherComment;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getAssignmentAdminName() {
		return assignmentAdminName;
	}

	public void setAssignmentAdminName(String assignmentAdminName) {
		this.assignmentAdminName = assignmentAdminName;
	}

	public Integer getWorkerRead() {
		return workerRead;
	}

	public void setWorkerRead(Integer workerRead) {
		this.workerRead = workerRead;
	}
}
