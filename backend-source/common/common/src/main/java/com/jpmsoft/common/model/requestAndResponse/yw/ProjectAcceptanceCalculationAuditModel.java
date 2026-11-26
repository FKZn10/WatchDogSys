package com.jpmsoft.common.model.requestAndResponse.yw;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectAcceptanceCalculationAuditModel extends BaseFileDto {
    private static final long serialVersionUID = 703509242427141354L;
    /**
     *
     */
    private String id;

    /**
     *
     */
    private String projectId;
    private String projectName;
    private String projectType;

    /**
     *
     */
    private String contractId;
    private String contractName;
    private String contractSecondCompanyName;

    /**
     * 是否跳过已完工未初验阶段
     */
    private Integer skipDoneStage;

    /**
     * 状态
     */
    private String status;

    /**
     * 完工时间
     */
    private Date doneTime;

    /**
     * 完工文件上传
     */
    private String doneFile;
    private String doneFileIds;

    /**
     * 制度规定最迟初验天数
     */
    private BigDecimal standardDays;

    /**
     * 拟定最迟初验日期
     */
    private Date draftDeadlineTime;

    /**
     * 初步验收日
     */
    private Date firstCheckTime;

    /**
     * 会议签到表
     */
    private String signInTableFile;
    private String signInTableFileIds;

    /**
     * 会议纪要
     */
    private String meetingSummaryFile;
    private String meetingSummaryFileIds;

    /**
     * 超过拟定最迟初验日天数
     */
    private BigDecimal overStandardDays;

    /**
     * 超过拟定最迟初验日开始时间
     */
    private Date overDraftDeadlineStartTime;

    /**
     * 超过拟定最迟初验日结束时间
     */
    private Date overDraftDeadlineEndTime;

    /**
     * 制度规定最迟竣工验收天数
     */
    private BigDecimal standardCompleteDays;

    /**
     * 拟定最迟竣工验收日期
     */
    private Date draftCompleteDeadlineTime;
    /**
     * 除外责任情况
     */
    private String excludedResponsibility;
    /**
     * 除外责任情况文件
     */
    private String excludedResponsibilityFile;
    private String excludedResponsibilityFileIds;
    /**
     * 竣工日期
     */
    private Date completeTime;

    /**
     * 竣工会议签到表
     */
    private String completeSignInTableFile;
    private String completeSignInTableFileIds;

    /**
     * 竣工会议纪要
     */
    private String completeMeetingSummaryFile;
    private String completeMeetingSummaryFileIds;

    /**
     * 超过拟定最迟竣工验收天数
     */
    private BigDecimal overStandardCompleteDays;

    /**
     * 超过拟定最迟竣工验收开始时间
     */
    private Date overDraftCompleteDeadlineStartTime;

    /**
     * 超过拟定最迟竣工验收结束时间
     */
    private Date overDraftCompleteDeadlineEndTime;

    /**
     * 制度规定最迟结算送审天数
     */
    private BigDecimal standardSettlementDays;

    /**
     * 拟定最迟结算送审日期
     */
    private Date draftSettlementDeadlineTime;
    /**
     * 合同有无约定
     */
    private Integer isContractAgreement;
    /**
     * 合同约定天数
     */
    private BigDecimal contractAgreementDays;
    /**
     * 送审日期
     */
    private Date submitTime;

    /**
     * 送审基本情况表
     */
    private String submitBasicTableFile;
    private String submitBasicTableFileIds;

    /**
     * 超过拟定最迟结算送审天数
     */
    private BigDecimal overStandardSettlementDays;

    /**
     * 超过拟定最迟结算送审开始时间
     */
    private Date overDraftSettlementDeadlineStartTime;

    /**
     * 超过拟定最迟结算送审结束时间
     */
    private Date overDraftSettlementDeadlineEndTime;

    /**
     * 制度规定最迟结算审核天数
     */
    private BigDecimal standardAuditDays;

    /**
     * 拟定最迟结算审核日期
     */
    private Date draftAuditDeadlineTime;

    /**
     * 审定日期
     */
    private Date auditTime;

    /**
     * 审定单
     */
    private String auditFile;
    private String auditFileIds;

    /**
     * 超过拟定最迟结算审核天数
     */
    private BigDecimal overStandardAuditDays;

    /**
     * 超过拟定最迟结算审核开始时间
     */
    private Date overDraftAuditDeadlineStartTime;

    /**
     * 超过拟定最迟结算审核结束时间
     */
    private Date overDraftAuditDeadlineEndTime;
    private List<ZNPJEvaluateBasisInformationQuarterVo> znpjEvaluateBasisInformationQuarterVos;

    private List<String> times;
}