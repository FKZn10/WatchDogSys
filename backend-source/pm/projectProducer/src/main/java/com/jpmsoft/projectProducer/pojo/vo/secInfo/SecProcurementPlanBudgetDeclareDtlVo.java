package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SecProcurementPlanBudgetDeclareDtlVo {

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

   //剩余金额比例
   private Float residueProportion;

   //【预留字段】层级
   private Integer bddLevel;
   //【预留字段】类别
   private Integer bddType;
   //【预留字段】状态
   private Integer bddState;
   //【预留字段】上级路径
   private String bddPath;
   //【预留字段】是否叶子节点 1、是 0、否
   private Integer bddLeafNode;

   private List<SecProcurementPlanBudgetDeclareDtlVo> children;

}
