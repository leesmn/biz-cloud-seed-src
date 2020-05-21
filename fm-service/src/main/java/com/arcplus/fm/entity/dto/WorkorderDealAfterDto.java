package com.arcplus.fm.entity.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class WorkorderDealAfterDto extends WorkorderDealDto {
  /*
  材料费用
   */
   private BigDecimal materialCost;
   /*
   人工费用
    */
   private BigDecimal laborCost;
   /*
   备注
    */
   private String remark;
   /*
   客户签名
    */
   private String signPicture;
}
