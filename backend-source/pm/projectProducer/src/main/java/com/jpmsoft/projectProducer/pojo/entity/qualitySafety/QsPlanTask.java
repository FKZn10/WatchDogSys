package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡检任务
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/20 14:32
 */
@Data
@TableName("Qs_Plan_Task")
public class QsPlanTask extends BaseEntity {

    /**
     * 质量安全检查计划表主键id
     */
    private String qspId;
    /**
     * 项目ID
     */
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
     * 评估时间（检查日期）
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
     * 评估组组长姓名
     */
    private String ptGroupLeaderName;
    /**
     * 领队人姓名
     */
    private String ptTeamLeaderName;
    /**
     * 评估组成员姓名（文本输入）   检查人员
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
     * 任务类别 01、质量 02、安全
     */
    private String ptCategory;
    private String teId;
    private String ptMeasure;
    private Boolean ptIsConstruction;
    private String ptConcrete;
    private String ptRectifyReply;

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
    /**
     * 实际整改时间
     */
    private Date ptRectifyActualTime;
    //超期天数
    private BigDecimal overdueDays;
    /**
     * 城建超期状态
     * 1：正常完成
     * 2：临期
     * 3：超期未整改
     * 4: 超期完成
     */
    private String outTimeStatus;

    /**
     * 推送状态 01：未推送 02：推送成功
     */
    private String pushStatus;

}