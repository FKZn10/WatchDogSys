package com.jpmsoft.jpbpm.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 风险预警指标
 */
@Data
@TableName("Risk_Warn_Index")
public class RiskWarnIndex {

    @TableId(type = IdType.INPUT)
    private String rwiId;
    private String rwiCode;
    private String rwiType;
    private String rwiName;
    private String rwiDesc;
    private String rwiProblem;
    private String rwiCalculationRule;
    private String rwiValue;
    private String rwiBasis;
    private String rwiLevel;
    private String rwiCreateUid;
    private String rwiCreateUname;
    private Date rwiCreateTime;
    private Integer rwiState;
    private String dId;
    private String rwiPersonUid;
    private String rwiPersonUname;
    private Integer rwiProjectLeader;
    private String rwiBusinessInforUrl;
    private String rwiScreeningConditions;
    private String rwiQuerySql;
    private String rwiCcPost;
    private String teId;
    private String rwiCcPostFilter;
    private String rwiCcSmsType;
    private String rwiCcSmsTemplate;
}