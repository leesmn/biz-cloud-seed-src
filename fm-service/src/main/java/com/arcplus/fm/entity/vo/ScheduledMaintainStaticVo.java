package com.arcplus.fm.entity.vo;

import com.arcplus.fm.entity.BoScheduledMaintain;

public class ScheduledMaintainStaticVo extends BoScheduledMaintain {

    public String assignmentName;

    public String getScheduledRuleName(){
        return this.getScheduledRule().getValue();
    }

}
