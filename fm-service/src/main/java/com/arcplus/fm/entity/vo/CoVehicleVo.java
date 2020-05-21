package com.arcplus.fm.entity.vo;

import com.arcplus.fm.entity.CoVehicle;

public class CoVehicleVo extends CoVehicle {

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    private String branchCompanyName;

}
