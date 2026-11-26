package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 开标结果-候选人清单
 */
@Data
@TableName("Sec_Open_Result_Candidate")
public class SecOpenResultCandidate {
   @TableId(type = IdType.INPUT)
   private String orcId;
   //开评标结果表id
   private String orId;

   //候选人单位id
   private String orcCandidateId;

   //候选人单位名称
   private String orcCandidateName;

   //候选人报价/费率
   private BigDecimal orcQuote;

   //候选人组
   private Integer orcGroupNum;

   //组内排序
   private Integer orcSort;

}
