package com.jpmsoft.fileUpload.pojo.vo;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/20 14:40
 */
@Data
public class QsPlanTaskVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = 2409247783198662947L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String conName;
    private String conCode;
    private String ptContractorName;
    private String ptContractorAbbName;
    private String ptSupervisorName;
    private String ptSupervisorAbbName;
    private String ptEvaluationUnitName;
    private String ptEvaluationUnitAbbName;
    private String ptRectifyName;
    private String ptRectifyAbbName;
    private String ptPmName;
    private String ptCseName;
    private String ptGroupLeaderName;
    private String ptTeamLeaderName;
    private String qspCode;
    private String qspName;
    /**
     * 质量安全检查计划表主键id
     */
    private String qspId;
    /**
     * 合同ID
     */
    private String conId;
    /**
     * 施工单位ID
     */
    private String ptContractorId;
    /**
     * 监理单位ID
     */
    private String ptSupervisorId;
    /**
     * 施工单位项目经理ID
     */
    private String ptPm;
    /**
     * 监理单位项目总监ID
     */
    private String ptCse;
    /**
     * 评估组组长ID
     */
    private String ptGroupLeader;
    /**
     * 领队人ID
     */
    private String ptTeamLeader;
    /**
     * 评估组成员ID（多个）
     */
    private String ptGroupMember;
    /**
     * 评估时间
     */
    private Date ptEvaluationTime;
    /**
     * 评估单位ID
     */
    private String ptEvaluationUnitId;
    /**
     * 上期施工阶段
     */
    private String ptLastConstruction;
    /**
     * 本期施工阶段
     */
    private String ptConstruction;
    /**
     * 任务说明
     */
    private String ptDescription;
    /**
     * 是否实测实量
     */
    private Integer ptMrq;
    /**
     * 评估结果上传
     */
    private String ptEvaluationResult;
    /**
     * 评估组成员姓名（文本输入）
     */
    private String ptGroupMemberName;
    /**
     * 所属子公司
     */
    private String ptCid;

    /**
     * 所属子公司名称
     */
    private String ptCname;
    /**
     * 问题总数
     */
    private Integer ptProblemCount;
    /**
     * 要求整改时间
     */
    private Date ptRectifyRequireTime;
    /**
     * 实际整改时间
     */
    private Date ptRectifyActualTime;
    //超期天数
    private BigDecimal overdueDays;
    /**
     * 负责整改单位（ID）
     */
    private String ptRectifyCid;
    /**
     * 已整改完成数量
     */
    private Integer ptFinishCount;
    /**
     * 问题报告（文件上传）
     */
    private String ptProblemReport;
    /**
     * 整改报告（文件上传）
     */
    private String ptRectifyReport;
    /**
     * 巡检类型
     */
    private String ptType;
    /**
     * 整改完成时间
     */
    private Date ptRectifyTime;
    /**
     * 整改备注
     */
    private String ptRectifyRemark;
    /**
     * 编号
     */
    private String ptCode;
    /**
     * 较大及以上隐患问题描述
     */
    private String ptPitfall;
    /**
     * 整改回复情况
     */
    private String ptReply;

    /**
     * 任务类别
     */
    private String ptCategory;
    private String ptMeasure;
    private Boolean ptIsConstruction;
    private String ptConcrete;
    private String ptRectifyReply;

    /**
     * 问题报告（文件上传） IDS
     */
    private String ptProblemReportIds;
    /**
     * 整改报告（文件上传）IDS
     */
    private String ptRectifyReportIds;
    private List<QsRecordResponse> qsRecords;
    /**
     * 问题数量
     */
    private Integer qsRecordCount = 0;
    /**
     * 整改数量
     */
    private Integer qsFinishCount = 0;
    /**
     * 回复数量
     */
    private Integer qsReplyCount = 0;
    /**
     * 闭合率
     */
    private BigDecimal qsFinishRate;
    /**
     * 回复率
     */
    private BigDecimal qsReplyRate;
    //通知形式
    private String ptNoticeType;
    //存在问题
    private String ptProblem;
    //整改意见及建议
    private String ptRectifySuggest;
    //整改限期时间
    private Date ptRectifyLimitTime;
    //整改检察人员
    private String ptRectifyPerson;
    //检查名称
    private String ptName;

}
