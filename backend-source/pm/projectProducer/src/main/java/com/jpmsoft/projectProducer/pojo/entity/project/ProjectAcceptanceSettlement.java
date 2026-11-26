package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName("Project_Acceptance_Settlement")
@Data
public class ProjectAcceptanceSettlement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -29546832152263447L;
    private String id;
    private String projectId;
    private String projectNameTemp;
    private String contractId;
    private String projectType;
    private String constructionUnit;
    //开工日期
    private Date startDate;
    //初步验收日期
    private Date acceptanceDate;
    //制度规定竣工验收时限（天）
    private BigDecimal standardCompletionTime;
    //拟定最迟竣工验收日期
    private Date draftLatestCompletionDate;
    //实际竣工验收组日期
    private Date actualCompletionDate;
    //竣工验收超期（天）
    private BigDecimal completionOverdueDays;
    //是否已竣工验收 0：否 1：是
    private Integer isCompleted;
    //合同约定结算送审时限（天）
    private BigDecimal standardSettlementTime;
    //制度规定结算送审时间（天）
    private BigDecimal draftSettlementTime;
    //拟定最迟结算送审日期
    private Date draftLatestSettlementDate;
    //实际结算送审日期
    private Date actualSettlementDate;
    //结算送审超期（天）
    private BigDecimal settlementOverdueDays;
    //是否已结算送审 0：否 1：是
    private Integer isSettlementApproved;
    //制度规定结算审计时间（天）
    private BigDecimal standardAuditTime;
    //拟定最迟结算审定日期
    private Date draftLatestAuditDate;
    //实际审定日期
    private Date actualAuditDate;
    //审计超期（天）
    private BigDecimal auditOverdueDays;
    //是否已结算审定 0：否 1：是
    private Integer isAudited;
    //当前进展
    private String currentProgress;
    //竣工超期扣分
    private BigDecimal completionOverdueDeductScore;
    //结算送审超期扣分
    private BigDecimal settlementOverdueDeductScore;
    //结算审计超期扣分
    private BigDecimal auditOverdueDeductScore;
}
