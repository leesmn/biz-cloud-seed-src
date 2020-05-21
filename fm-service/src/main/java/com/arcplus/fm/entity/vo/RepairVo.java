package com.arcplus.fm.entity.vo;

import com.arcplus.fm.constant.EDealWith;
import com.arcplus.fm.entity.BoRepair;

import java.util.Optional;

public class RepairVo extends BoRepair {

    public String getDealStatusName() {
        return this.getDealStatus().getValue();
    }

    public String getDealWithName() {
        return Optional.ofNullable(this.getDealWith())
                .map(EDealWith::getValue)
                .orElse("");
    }

    public String branchCompanyName;

    public String failureTypeName;
}
