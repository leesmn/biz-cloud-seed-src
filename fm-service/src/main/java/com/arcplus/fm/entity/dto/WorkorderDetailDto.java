package com.arcplus.fm.entity.dto;

import com.arcplus.fm.entity.vo.WorkorderVo;
import com.arcplus.fm.entity.BoRepair;
import com.arcplus.fm.entity.BoWorkorderAttachment;
import com.arcplus.fm.entity.BoWorkorderProcess;
import java.util.List;
import lombok.Data;

@Data
public class WorkorderDetailDto {
    private WorkorderVo workorder;
    private BoRepair repair;
    private List<BoWorkorderProcess> workorderProcesses;
    private List<BoWorkorderAttachment> dealBeforAttachment;
    private List<BoWorkorderAttachment> dealAfterAttachment;
}
