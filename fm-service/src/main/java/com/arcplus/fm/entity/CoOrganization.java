package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;

import java.io.Serializable;
import java.util.Date;


/**
 * 共同-集团公司架构
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:32:06
 */
public class CoOrganization implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //分公司id
    private Integer orgId;
	
	    //分公司名称
    private String orgName;
	
	    //分公司描述
    private String orgDescribe;
	
	    //上级公司id
    private Integer parentId;

    	//color
    private String color;
	
	    //创建者
    private Integer createBy;
	
	    //创建时间
    private Date createAt;
	
	    //更新者
    private Integer updateBy;
	
	    //更新时间
    private Date updateAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：分公司id
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：分公司id
	 */
	public Integer getOrgId() {
		return orgId;
	}
	/**
	 * 设置：分公司名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * 获取：分公司名称
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * 设置：分公司描述
	 */
	public void setOrgDescribe(String orgDescribe) {
		this.orgDescribe = orgDescribe;
	}
	/**
	 * 获取：分公司描述
	 */
	public String getOrgDescribe() {
		return orgDescribe;
	}
	/**
	 * 设置：上级公司id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级公司id
	 */
	public Integer getParentId() {
		return parentId;
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
	 * 设置：更新者
	 */
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新者
	 */
	public Integer getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateAt() {
		return updateAt;
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

	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
}
