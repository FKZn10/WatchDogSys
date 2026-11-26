package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 洽商费用申请
 *
 * @Author: 李文涛 aaron
 * @date: 2023/12/21 17:00
 */
@Data
@TableName("Con_En_Fee")
public class ConEnFee extends BaseEntity {

    private String enisId;
    private String pId;
    private String conId;
    private String cefCode;
    private String cefReason;
    private String cefLocation;
    private BigDecimal cefBudgetAmt;
    private String teId;

    private BigDecimal cefDeclaredAmt;
    private BigDecimal cefConRate;
    private String cefApplicant;
    private String cefContractorId;
    private String cefSupervisorId;
    private String cefPm;
    private Integer cefDuration;
    private String cefRemark;
    private String cefImplementation;

}
