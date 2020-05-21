package com.arcplus.fm.entity;

import com.arcplus.fm.constant.EDataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 共同-合同
 *
 * @author jv_team
 * @email
 * @date 2020-05-07 15:02:03
 */
public class CoContract implements Serializable {
    private static final long serialVersionUID = 1L;

    //合同id
    private Integer contractId;

    //合同号
    private String contractNo;

    //合同名称
    @NotNull(message = "合同名称必填")
    private String contractName;

    //合同编号
    @NotNull(message = "合同编号必填")
    private String contractCode;

    //合同对象
    @NotNull(message = "合同对象必填")
    private Integer contractTarget;

    //发票抬头
    @NotNull(message = "发票抬头必填")
    private String invoiceTitle;

    //合同生效日期
    @NotNull(message = "合同生效日期必填")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    //合同截至日期
    @NotNull(message = "合同截至日期必填")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    //合同面积
    @NotNull(message = "合同面积必填")
    private Double contractArea;

    //合同金额
    @NotNull(message = "合同金额必填")
    private BigDecimal contractCost;

    //结算方式;'M'月付,'Q'季付，‘D’其他
    @NotNull(message = "结算方式必填")
    private String settlementMethod;

    //备注
    private String remark;

    //附件服务器地址
    @NotNull(message = "附件必填")
    private String attachment;

    //合同状态:有效effective 失效invalidated 未激活inactive 已过期expire
    private String contractStatus;

    //父合同id（默认0）
    private Integer parentContract;

    //创建时间
    private Date createAt;

    //创建者
    private Integer createBy;

    //创建者名
    private String creatorName;

    //更新者
    private Integer updateBy;

    //更新时间
    private Date updateAt;

    //N,正常；D,删除
    private EDataStatus dataStatus;


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


    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置：合同名称
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * 获取：合同名称
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * 设置：合同编号
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * 获取：合同编号
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 设置：合同对象
     */
    public void setContractTarget(Integer contractTarget) {
        this.contractTarget = contractTarget;
    }

    /**
     * 获取：合同对象
     */
    public Integer getContractTarget() {
        return contractTarget;
    }

    /**
     * 设置：发票抬头
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    /**
     * 获取：发票抬头
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * 设置：合同生效日期
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * 获取：合同生效日期
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * 设置：合同截至日期
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * 获取：合同截至日期
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * 设置：合同面积
     */
    public void setContractArea(Double contractArea) {
        this.contractArea = contractArea;
    }

    /**
     * 获取：合同面积
     */
    public Double getContractArea() {
        return contractArea;
    }

    /**
     * 设置：合同金额
     */
    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    /**
     * 获取：合同金额
     */
    public BigDecimal getContractCost() {
        return contractCost;
    }

    /**
     * 设置：结算方式;'M'月付,'Q'季付，‘D’其他
     */
    public void setSettlementMethod(String settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    /**
     * 获取：结算方式;'M'月付,'Q'季付，‘D’其他
     */
    public String getSettlementMethod() {
        return settlementMethod;
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
     * 设置：附件服务器地址
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * 获取：附件服务器地址
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置：合同状态:有效effective 失效invalidated 未激活inactive 已过期expire
     */
    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    /**
     * 获取：合同状态:有效effective 失效invalidated 未激活inactive 已过期expire
     */
    public String getContractStatus() {
        return contractStatus;
    }

    /**
     * 设置：父合同id（默认0）
     */
    public void setParentContract(Integer parentContract) {
        this.parentContract = parentContract;
    }

    /**
     * 获取：父合同id（默认0）
     */
    public Integer getParentContract() {
        return parentContract;
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
     * 设置：创建者名
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * 获取：创建者名
     */
    public String getCreatorName() {
        return creatorName;
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
