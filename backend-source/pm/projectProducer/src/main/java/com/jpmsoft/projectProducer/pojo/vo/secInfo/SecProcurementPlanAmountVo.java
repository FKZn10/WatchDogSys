package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SecProcurementPlanAmountVo {

   private String bddId;
   //概算申报主表id
   private String bdId;
   //序号
   private String bddNumber;
   //上级ID
   private String bddParentId;
   //清单编码
   private String bddCode;
   //项目及费用名称
   private String bddName;
   //计量单位名称
   private String bddUnit;
   //工程量
   private Float bddCount;
   //单价
   private BigDecimal bddPrice;
   //合价（投资）
   private BigDecimal bddTotal;


}
