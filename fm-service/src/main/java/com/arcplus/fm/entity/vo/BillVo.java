package com.arcplus.fm.entity.vo;

import com.arcplus.fm.entity.CoBill;

import java.math.BigDecimal;
import java.util.Date;

public class BillVo extends CoBill {

    public BigDecimal getFinalCost() {

        if (null == this.getRealIncomeCost()
                || null == this.getExtraCost()) {
            return new BigDecimal(0);
        }

        return this.getRealIncomeCost()
                .add(this.getExtraCost());
    }

    public String getBillStatus() {
        switch (super.getInvoiceStatus()) {
            case "Y":
                return "已开票";
            case "N":
                return "未开票";
            case "P":
                return "已付款";
            default:
                return "";
        }
    }

    public String customerName;

    public Date contractCreateTime;

    public String contractCode;
}
