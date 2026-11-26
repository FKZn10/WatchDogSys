package com.jpmsoft.projectProducer.pojo.entity.yw;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("project_acceptance_calculation_audit")
public class ProjectAcceptanceCalculationAudit implements Serializable {
    private static final long serialVersionUID = 1060890755453983578L;
    /**
     *
     */
    private String id;

    /**
     *
     */
    private String projectId;

    /**
     *
     */
    private String contractId;

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

    /**
     * 会议纪要
     */
    private String meetingSummaryFile;

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
    /**
     * 竣工日期
     */
    private Date completeTime;

    /**
     * 竣工会议签到表
     */
    private String completeSignInTableFile;
    /**
     * 竣工会议纪要
     */
    private String completeMeetingSummaryFile;

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

    /**
     *
     */
    private String createUid;

    /**
     *
     */
    private String createUname;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private Date stateTime;
}