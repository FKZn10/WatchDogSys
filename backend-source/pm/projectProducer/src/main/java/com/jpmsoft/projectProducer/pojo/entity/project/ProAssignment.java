package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目分派单
 */
@Data
@TableName("Pro_Assignment")
public class ProAssignment {

    @TableId(type = IdType.INPUT)
    private String paId;
    private String pId;
    private String paCode;
    private String paImplementationStatus;
    private String paUserUnitName;
    private String paApprovalNo;
    private Date paApprovalDate;
    private String paApprovalUnit;
    private String paConcentrateUnitId;
    private BigDecimal paInvestment;
    private Date paStartDate;
    private Date paEndDate;
    private String paProjectOverview;
    private String paUnitId;
    private String paContent;
    private String paProposal;
    private String paRemark;
    private String paCreateUid;
    private String paCreateUname;
    private Date paCreateTime;
    private Integer paState;
    private Date paStateTime;
    private String paContentOther;
    private Integer paStartYear;
    private Integer paEndYear;
    private String teId;

    private Boolean paPayAudit;
    private Boolean paBidCompiling;
    private String paAgentWay;
    private String paAddress;
    private String paDuration;
}
