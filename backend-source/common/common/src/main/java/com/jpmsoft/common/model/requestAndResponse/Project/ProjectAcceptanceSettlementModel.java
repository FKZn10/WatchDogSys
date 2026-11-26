package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectAcceptanceSettlementModel extends BaseFileDto {
    private static final long serialVersionUID = 5322837251173764596L;
    private String id;
    private String projectId;
    private String projectName;
    private String projectNameTemp;
    private String contractId;
    private String contractName;
    private String projectType;
    private String constructionUnit;
    private String constructionUnitName;
    //开工日期
    private Date startDate;
    //初步验收日期
    private Date acceptanceDate;
    //制度规定竣工验收时限（天）
    private BigDecimal standardCompletionTime;
    //拟定最迟竣工验收日期  初步验收日期+制度规定竣工验收时限（天）
    private Date draftLatestCompletionDate;
    //实际竣工验收组日期
    private Date actualCompletionDate;
    //竣工验收超期（天） MIN(TODAY(),实际竣工验收组日期)-拟定最迟竣工验收日期
    private BigDecimal completionOverdueDays;
    //是否已竣工验收 0：否 1：是 IF(ISBLANK(实际竣工验收组日期),"否","是")
    private Integer isCompleted;
    //合同约定结算送审时限（天）
    private BigDecimal standardSettlementTime;
    //制度规定结算送审时间（天）
    private BigDecimal draftSettlementTime;
    //拟定最迟结算送审日期
    private Date draftLatestSettlementDate;
    //实际结算送审日期
    private Date actualSettlementDate;
    //结算送审超期（天） MIN(TODAY(),实际结算送审日期)-拟定最迟结算送审日期
    private BigDecimal settlementOverdueDays;
    //是否已结算送审 0：否 1：是 IF(ISBLANK(实际结算送审日期),"否","是")
    private Integer isSettlementApproved;
    //制度规定结算审计时间（天）
    private BigDecimal standardAuditTime;
    //拟定最迟结算审定日期  MAX(拟定最迟结算送审日期,实际结算送审日期)+制度规定结算审计时间（天）
    private Date draftLatestAuditDate;
    //实际审定日期
    private Date actualAuditDate;
    //审计超期（天） MIN(TODAY(),实际审定日期)-拟定最迟结算审定日期
    private BigDecimal auditOverdueDays;
    //是否已结算审定 0：否 1：是 IF(ISBLANK(实际审定日期),"否","是")
    private Integer isAudited;
    //当前进展
    private String currentProgress;
    /**
     * 竣工超期扣分
     * IF(是否已竣工验收="是",0,IF(AND(制度规定竣工验收时限（天）<竣工验收超期（天）<=2*制度规定竣工验收时限（天）),
     * -1,IF(AND(2*制度规定竣工验收时限（天）<竣工验收超期（天）<=3*制度规定竣工验收时限（天）),-2,IF(3*制度规定竣工验收时限（天）<竣工验收超期（天）,-3,0))))
     */
    private BigDecimal completionOverdueDeductScore;
    /**
     * 结算送审超期扣分  IF(AND(是否已竣工验收="是",是否已结算送审="是"),0,
     * IF(AND(是否已竣工验收="是",是否已结算送审="否"),
     * 合同约定结算送审时限（天）+制度规定结算送审时间（天）<结算送审超期（天）<=合同约定结算送审时限（天）+2*制度规定结算送审时间（天） -1
     * 合同约定结算送审时限（天）+2*制度规定结算送审时间（天）<结算送审超期（天）<=合同约定结算送审时限（天）+3*制度规定结算送审时间（天 ） -2
     * 合同约定结算送审时限（天）+3*制度规定结算送审时间（天）<结算送审超期（天）-3
     *
     */
    private BigDecimal settlementOverdueDeductScore;
    /**
     * 结算审计超期扣分
     * K2="是",Q2="是",V2="是",0
     * K2="是",Q2="是",V2="否",
     * R2<U2<=2*R2,-1,
     * 2*R2<U2<=3*R2,-2
     * 3*R2<U2,-3
     */
    private BigDecimal auditOverdueDeductScore;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
}
