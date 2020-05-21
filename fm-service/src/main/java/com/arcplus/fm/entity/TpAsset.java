package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;

import java.io.Serializable;
import java.util.Date;


/**
 * 共同-资产分类
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:31:12
 */
public class TpAsset implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //资产规格类型id
    private Integer assetId;
	
	    //故障类型名称
    private String assetName;
	
	    //故障类型描述
    private String assetDescribe;
	
	    //上级id
    private Integer parentId;
	
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
	 * 设置：资产规格类型id
	 */
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
	/**
	 * 获取：资产规格类型id
	 */
	public Integer getAssetId() {
		return assetId;
	}
	/**
	 * 设置：故障类型名称
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	/**
	 * 获取：故障类型名称
	 */
	public String getAssetName() {
		return assetName;
	}
	/**
	 * 设置：故障类型描述
	 */
	public void setAssetDescribe(String assetDescribe) {
		this.assetDescribe = assetDescribe;
	}
	/**
	 * 获取：故障类型描述
	 */
	public String getAssetDescribe() {
		return assetDescribe;
	}
	/**
	 * 设置：上级id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级id
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
}
