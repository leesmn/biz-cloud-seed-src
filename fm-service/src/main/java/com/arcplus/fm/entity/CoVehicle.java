package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-08 10:15:32
 */
public class CoVehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    private Integer vehicleId;
	
	    //车牌号码
    private String carNum;
	
	    //车主姓名
    private String userName;
	
	    //联系电话
    private String contactPhone;

	    //分公司id
    private String branchCompanyId;
	
	    //楼层id
    private Integer floorId;
	
	    //楼层名称
    private String floorName;
	
	    //楼栋id
    private Integer buildingId;
	
	    //楼栋名称 
    private String buildingName;
	
	    //'W',待处理；'P',处理中；'F',已完成；'I',已忽略
    private EDealStatus dealStatus;
	
	    //空间编号---bim
    private String space;
	//空间名称---bim
	private String spaceName;
	    //
    private Integer deleteUid;
	
	    //
    private Date deleteTime;
	
	    //
    private Date lastModifierTime;
	
	    //
    private Integer lastModifierUid;
	
	    //
    private Date createAt;
	
	    //
    private Integer createBy;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：
	 */
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	/**
	 * 获取：
	 */
	public Integer getVehicleId() {
		return vehicleId;
	}
	/**
	 * 设置：车牌号码
	 */
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	/**
	 * 获取：车牌号码
	 */
	public String getCarNum() {
		return carNum;
	}
	/**
	 * 设置：车主姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：车主姓名
	 */
	public String getUserName() {
		return userName;
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
	 * 设置：分公司id
	 */
	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
	/**
	 * 获取：分公司id
	 */
	public String getBranchCompanyId() {
		return branchCompanyId;
	}
	/**
	 * 设置：楼层id
	 */
	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}
	/**
	 * 获取：楼层id
	 */
	public Integer getFloorId() {
		return floorId;
	}
	/**
	 * 设置：楼层名称
	 */
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	/**
	 * 获取：楼层名称
	 */
	public String getFloorName() {
		return floorName;
	}
	/**
	 * 设置：楼栋id
	 */
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	/**
	 * 获取：楼栋id
	 */
	public Integer getBuildingId() {
		return buildingId;
	}
	/**
	 * 设置：楼栋名称 
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	/**
	 * 获取：楼栋名称 
	 */
	public String getBuildingName() {
		return buildingName;
	}
	/**
	 * 设置：//'W',待处理；'P',处理中；'F',已完成；'I',已忽略
	 */
	public void setDealStatus(EDealStatus dealStatus) {
		this.dealStatus = dealStatus;
	}
	/**
	 * 获取：//'W',待处理；'P',处理中；'F',已完成；'I',已忽略
	 */
	public EDealStatus getDealStatus() {
		return dealStatus;
	}
	/**
	 * 设置：空间编号---bim
	 */
	public void setSpace(String space) {
		this.space = space;
	}
	/**
	 * 获取：空间编号---bim
	 */
	public String getSpace() {
		return space;
	}
	/**
	 * 设置：
	 */
	public void setDeleteUid(Integer deleteUid) {
		this.deleteUid = deleteUid;
	}
	/**
	 * 获取：
	 */
	public Integer getDeleteUid() {
		return deleteUid;
	}
	/**
	 * 设置：
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	/**
	 * 获取：
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
	/**
	 * 设置：
	 */
	public void setLastModifierTime(Date lastModifierTime) {
		this.lastModifierTime = lastModifierTime;
	}
	/**
	 * 获取：
	 */
	public Date getLastModifierTime() {
		return lastModifierTime;
	}
	/**
	 * 设置：
	 */
	public void setLastModifierUid(Integer lastModifierUid) {
		this.lastModifierUid = lastModifierUid;
	}
	/**
	 * 获取：
	 */
	public Integer getLastModifierUid() {
		return lastModifierUid;
	}
	/**
	 * 设置：
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	/**
	 * 获取：
	 */
	public Date getCreateAt() {
		return createAt;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
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

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
}
