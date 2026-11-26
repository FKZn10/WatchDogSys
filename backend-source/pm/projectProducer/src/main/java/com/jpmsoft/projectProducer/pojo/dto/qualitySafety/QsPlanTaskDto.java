package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 巡检任务
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/20 14:32
 */
@Data
public class QsPlanTaskDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = -6538085246674533813L;
    /**
     * 质量安全检查计划表主键id
     */
    private String qspId;
    /**
     * 项目ID
     */
    @JsonProperty("pId")
    private String pId;
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
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

    private String rectifyStatus;
    /**
     * 评估组组长姓名
     */
    private String ptGroupLeaderName;
    /**
     * 领队人姓名
     */
    private String ptTeamLeaderName;
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date ptRectifyRequireTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
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
    private String  ptCategory;
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


    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String ptPmName;
    private String ptCseName;

    @JsonProperty("pType")
    private String pType;

    private String qstPath;

    private String qstId;

    private List<String> qstIds;

    private String projectName;

    private List<String> ptEvaluationTimeFilters;

    private String ptEvaluationTimeFilterStart;


    private String ptEvaluationTimeFilterEnd;
    /**
     * 整改单位名称
     */
    private String ptRectifyName;
    private String ptContractorName;
    private String ptSupervisorName;
    /**
     * 质量安全检查记录
     */
    private List<QsRecordRequest> qsRecords;
    private List<String> ptEvaluationTimes;
    //通知形式
    private String ptNoticeType;
    //存在问题
    private String ptProblem;
    //整改意见及建议
    private String ptRectifySuggest;
    //整改限期时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date ptRectifyLimitTime;
    //整改检察人员
    private String ptRectifyPerson;
    //检查名称
    private String ptName;
    /**
     * 是否超期（当前查询时间大于整改完成时间、并且是整改未完成状态）
     * 针对子表数据未整改完成的情况
     * 0、不超期（子表所有整改都没超期） 1、超期（：子表有一条数据就算）  空、null：不限
     */
    private Integer overdueTask;
    /**
     * 城建超期状态
     * 1：正常完成
     * 2：临期
     * 3：超期未整改
     * 4: 超期完成
     */
    private String outTimeStatus;

    private List<String> ptRectifyTimes;
}