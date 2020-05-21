package com.arcplus.fm.entity.vo;

import com.arcplus.fm.entity.CoContract;

import java.util.List;

public class ContractVo extends CoContract {

    public String contractTargetName;

    public int additionalContractCount;

    public String getSettlementMethodName() {

        switch (super.getSettlementMethod()) {
            case "M":
                return "月付";
            case "Q":
                return "季付";
            case "D":
                return "其他";
            default:
                return "";
        }
    }

    public String getContractStatusName() {

        switch (super.getContractStatus()) {
            case "inactive":
                return "未激活";
            case "effective":
                return "有效";
            case "expire":
                return "已过期";
            case "invalidated":
                return "失效";
            default:
                return "";
        }
    }

    public List<ContractVo> child ;

}
