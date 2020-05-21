package com.arcplus.fm.entity.vo;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EWoStatus;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class WorkorderVo {
     //工单id
     private Integer woId;

     //报修单号
     private String repairCode;

     //工单编号
     private String woCode;

     //设备编码
     private String facilityCode;

     //设备名称
     private String facilityName;

     //处理人
     private Integer assignment;

     //执行人姓名
     private String assignmentName;

     //处理组
     private Integer assignmentGroup;

     //处理组组长
     private Integer assignmentGroupAdmin;

     //处理组组长姓名
     private String assignmentAdminName;

     //优先级别，1-5（红,，橙，黄，绿，蓝）
     private Integer priorityLevel;

     /*
       CR("创建并分配班组"),
       AD("班组拒绝"),
       AM("班组接单并指派维修工"),
       MA("维修工接单"),
       MD("维修工拒绝"),
       MB("维修工处理前"),
       MO("维修工处理完成"),
       AC("班组确认完成"),
       AR("班组驳回"),
       CL("已结束");
   */
     private EWoStatus dealStatus;

     //维修工已读；0，未读；1，已读
     private Integer workerRead;

     //创建者
     private Integer createBy;

     //创建时间
     private Date createAt;

     //N,正常；D,删除
     private EDataStatus dataStatus;

     //班组长反馈意见
     private String groupAdminDescribe;

     //维修人员处理前，描述
     private String preDealDescribe;

     //维修人员处理后，描述
     private String sufDealDescribe;

     //备注
     private String remark;

     //材料费
     private BigDecimal materialCost;

     //人工费
     private BigDecimal laborCost;

     //总费用
     private BigDecimal totalCost;

     //业主签字
     private String signPicture;

     //故障类型id
     private Integer failureTypeId;

     /*
     回访结果
      */
     private String visitResult;

     /*
     其他意见
      */
     private String otherComment;

     private String statusDescr;
}
