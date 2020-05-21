package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import java.io.Serializable;
import java.util.Date;


/**
 * 共同-故障类型
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-15 16:13:22
 */
public class TpFailure implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //故障类型id
    private Integer failureId;
	
	    //故障类型名称
    private String failureName;
	
	    //故障类型描述
    private String failureDescribe;
	
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

    	//
    private String index;

	/**
	 * 设置：故障类型id
	 */
	public void setFailureId(Integer failureId) {
		this.failureId = failureId;
	}
	/**
	 * 获取：故障类型id
	 */
	public Integer getFailureId() {
		return failureId;
	}
	/**
	 * 设置：故障类型名称
	 */
	public void setFailureName(String failureName) {
		this.failureName = failureName;
	}
	/**
	 * 获取：故障类型名称
	 */
	public String getFailureName() {
		return failureName;
	}
	/**
	 * 设置：故障类型描述
	 */
	public void setFailureDescribe(String failureDescribe) {
		this.failureDescribe = failureDescribe;
	}
	/**
	 * 获取：故障类型描述
	 */
	public String getFailureDescribe() {
		return failureDescribe;
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


	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex() {
		return index;
	}
}
