package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yb.Sun   投资概算分解
 * @date 2024/11/1 14:59
 */
@Data
@TableName("Investment_Budget_Breakdown")
public class InvestmentBudgetBreakdown extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7344164938919112661L;
    private String id;
    //估算表id
    private String ieId;
    private String projectId;
    private String projectBudgetFile;
    //概算总投资（万元）
    private BigDecimal projectBudgetInvestmentAmount;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private BigDecimal totalInvestment;


    private String investMentFile;
}
