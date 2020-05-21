package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EDealWith;
import java.io.Serializable;
import java.util.Date;


/**
 * 共同-系统版本
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-07 18:13:05
 */
public class CoVersion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //版本id
    private Integer versionId;
	
	    //版本号
    private String versionNo;
	
	    //版本更新内容
    private String releaseContent;
	
	    //发布日期
    private Date releaseAt;
	
	    //创建者
    private Integer createBy;
	
	    //创建时间
    private Date createAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：版本id
	 */
	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	/**
	 * 获取：版本id
	 */
	public Integer getVersionId() {
		return versionId;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * 获取：版本号
	 */
	public String getVersionNo() {
		return versionNo;
	}
	/**
	 * 设置：版本更新内容
	 */
	public void setReleaseContent(String releaseContent) {
		this.releaseContent = releaseContent;
	}
	/**
	 * 获取：版本更新内容
	 */
	public String getReleaseContent() {
		return releaseContent;
	}
	/**
	 * 设置：发布日期
	 */
	public void setReleaseAt(Date releaseAt) {
		this.releaseAt = releaseAt;
	}
	/**
	 * 获取：发布日期
	 */
	public Date getReleaseAt() {
		return releaseAt;
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
