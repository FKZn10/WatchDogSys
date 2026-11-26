package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资概算分解变更
 */
@Data
@TableName("Investment_Budget_Breakdown_change")
public class InvestmentBudgetBreakdownChange extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7344164938919112661L;
    private String id;
    //估算表id
    private String ieId;
    private String projectId;
    private String originApplyId;
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
}
