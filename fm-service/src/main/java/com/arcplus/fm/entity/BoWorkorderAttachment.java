package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import java.io.Serializable;
import java.util.Date;


/**
 * 工单附件表
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-09 17:19:20
 */
public class BoWorkorderAttachment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    private Integer attachmentId;
	
	    //工单编号
    private String woCode;
	
	    //附件的地址
    private String attachmentUrl;
	
	    //0，处理前；1，处理后
    private Integer attachmentType;
	
	    //创建时间
    private Date createAt;
	
	    //N,正常；D,删除
    private EDataStatus dataStatus;
	

	/**
	 * 设置：
	 */
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}
	/**
	 * 获取：
	 */
	public Integer getAttachmentId() {
		return attachmentId;
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
	 * 设置：附件的地址
	 */
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	/**
	 * 获取：附件的地址
	 */
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	/**
	 * 设置：0，处理前；1，处理后
	 */
	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}
	/**
	 * 获取：0，处理前；1，处理后
	 */
	public Integer getAttachmentType() {
		return attachmentType;
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
