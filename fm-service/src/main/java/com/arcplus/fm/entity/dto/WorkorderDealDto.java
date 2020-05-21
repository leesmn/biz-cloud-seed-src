package com.arcplus.fm.entity.dto;

import com.arcplus.fm.entity.BoWorkorderAttachment;
import java.util.List;
import lombok.Data;

@Data
public class WorkorderDealDto {
    /*
    工单编号
     */
    private String woCode;
    /*
    设备编号
     */
    private String facilityCode;
    /*
    反馈信息
     */
    private String feedbackInfo;

    /*
    附件列表
     */
    private List<BoWorkorderAttachment> attachmentArray;
}
