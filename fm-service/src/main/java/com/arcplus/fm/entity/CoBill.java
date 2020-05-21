package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 共同-账单
 *
 * @author jv_team
 * @email
 * @date 2020-05-08 14:32:18
 */
public class CoBill implements Serializable {
	private static final long serialVersionUID = 1L;

	//账单id
	private Integer billId;

	//合同id
	private Integer contractId;

	//账单号
	private String billCode;

	//账单周期
	private String billPeriod;

	//付款对象
	private Integer paymentObject;

	//实收金额
	private BigDecimal realIncomeCost;

	//调整金额
	private BigDecimal extraCost;

	//开票状态;'Y'已开票,'N'未开票,'P'已付款
	private String invoiceStatus;

	//备注
	private String remark;

	//创建时间
	private Date createAt;

	//创建者
	private Integer createBy;

	//更新者
	private Integer updateBy;

	//更新时间
	private Date updateAt;

	//N,正常；D,删除
	private EDataStatus dataStatus;


	/**
	 * 设置：账单id
	 */
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	/**
	 * 获取：账单id
	 */
	public Integer getBillId() {
		return billId;
	}
	/**
	 * 设置：合同id
	 */
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	/**
	 * 获取：合同id
	 */
	public Integer getContractId() {
		return contractId;
	}
	/**
	 * 设置：账单号
	 */
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	/**
	 * 获取：账单号
	 */
	public String getBillCode() {
		return billCode;
	}
	/**
	 * 设置：账单周期
	 */
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	/**
	 * 获取：账单周期
	 */
	public String getBillPeriod() {
		return billPeriod;
	}
	/**
	 * 设置：付款对象
	 */
	public void setPaymentObject(Integer paymentObject) {
		this.paymentObject = paymentObject;
	}
	/**
	 * 获取：付款对象
	 */
	public Integer getPaymentObject() {
		return paymentObject;
	}
	/**
	 * 设置：实收金额
	 */
	public void setRealIncomeCost(BigDecimal realIncomeCost) {
		this.realIncomeCost = realIncomeCost;
	}
	/**
	 * 获取：实收金额
	 */
	public BigDecimal getRealIncomeCost() {
		return realIncomeCost;
	}
	/**
	 * 设置：调整金额
	 */
	public void setExtraCost(BigDecimal extraCost) {
		this.extraCost = extraCost;
	}
	/**
	 * 获取：调整金额
	 */
	public BigDecimal getExtraCost() {
		return extraCost;
	}
	/**
	 * 设置：开票状态;'Y'已开票,'N'未开票,'P'已付款
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	/**
	 * 获取：开票状态;'Y'已开票,'N'未开票,'P'已付款
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
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