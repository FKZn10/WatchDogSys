package com.jpmsoft.projectProducer.pojo.entity.visaChange;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计变更单
 */
@Data
@TableName("Con_Dco")
public class ConDco {

    @TableId(type = IdType.INPUT)
    private String dcoId;
    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同ID
     */
    private String conId;
    /**
     * 编号
     */
    private String dcoCode;
    /**
     * 工程名称
     */
    private String dcoProjectName;
    /**
     * 建设单位
     */
    private String dcoConstructionUnitId;
    /**
     * 设计单位
     */
    private String dcoDesignUnitId;
    /**
     * 监理单位
     */
    private String dcoSupervisorId;
    /**
     * 施工单位
     */
    private String dcoContractorId;
    /**
     * 专业名称
     */
    private String dcoMajor;
    /**
     * 变更日期
     */
    private Date dcoDate;
    /**
     * 变更内容及原因
     */
    private String dcoContent;
    /**
     * 设计变更通知单上传
     */
    private String dcoDoc;
    /**
     * 变更图纸上传
     */
    private String dcoDrawing;
    /**
     * 变更洽商单ID
     */
    private String enisId;
    /**
     * 工程变更造价书文件
     */
    private String dcoCostBook;
    /**
     * 变更分级
     */
    private String dcoGrade;
    /**
     * 估算金额
     */
    private BigDecimal dcoEstimate;

    private String dcoCreateUid;
    private String dcoCreateUname;
    private Date dcoCreateTime;
    private Integer dcoState;
    private Date dcoStateTime;
    private Boolean dcoYesNo;
    private Date dcoAdoptTime;
    private Boolean dcoIsHistory;
    private String dcoDep;

    private String dcoName;
    private String dcoApprovalMatter;
    private String teId;
    private String dcoSupervisionOpinion;
    private BigDecimal dcoSupervisionAmt;
    private String dcoSupervisionFile;
    private String dcoConsultingOpinion;
    private BigDecimal dcoConsultingAmt;
    private String dcoConsultingFile;
    private BigDecimal dcoFinalAmt;
    private BigDecimal dcoEstimatedAmt;
    private BigDecimal dcoConstructionAmt;
    private String dcoExceedBudget;
    private BigDecimal dcoIncrease;
    private Date dcoDirectorTime;
    private String dcoSummaryMust;
    private String dcoSummary;
    private Date dcoAuthorizeTime;
    private String dcoReviewFile;

    private BigDecimal dcoContractorAmt;
    private String dcoProposeId;

    private String dcoMainCode;
    private String dcoSubCode;

    /**
     * EPC单位意见
     */

    // EPC牵头单位意见
    private String epcUnitOpinion;

    // 审核金额
    private BigDecimal epcAuditAmt;

    // EPC牵头单位意见附件
    private String epcUnitFile;

    /**
     * 代建单位意见
     */

    // 代建单位变更名称
    private String dcoBuilderContractorName;

    // 代建单位意见合同金额
    private BigDecimal dcoBuilderContractorAmt;

    // 设计变更送审价预计
    private BigDecimal dcoBuilderContractorEstimate;

    // 现场签证送审价
    private BigDecimal dcoBuilderContractorSvis;

    // 预计备案费用
    private BigDecimal dcoBuilderContractorEstimateRecord;

    // 现场签证代建单位是否会同监理、施工单位共同确认 单选项（选项内容 是/否）
    private String dcoBuilderContractorIsConfirm;

    // 附件上传（需包含共同确认相关书面材料）
    private String dcoBuilderContractorFile;

    /**
     * 造价管理部意见
     */

    // 造价单位审核成果附件
    private String dcoCostBookFile;

    /**
     * 专家论证会
     */

    // 变更原因、责任分类说明
    private String dcoExpertReason;

    // 变更原因及依据
    private String dcoExpertReasonBasis;

    // 变更范围及内容
    private String dcoExpertScope;

    // 变更方案（上传ppt）附件
    private String dcoExpertPlanFile;

}
