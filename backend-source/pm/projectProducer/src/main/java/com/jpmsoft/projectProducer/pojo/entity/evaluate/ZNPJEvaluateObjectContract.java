package com.jpmsoft.projectProducer.pojo.entity.evaluate;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 合同评价
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("znpj_evaluate_object_contract")
public class ZNPJEvaluateObjectContract {


    private String id;

    /**
     * 版本id
     */
    private String versionId;

    /**
     * 版本号
     */
    private Integer versionNum;

    /**
     * 是否最新数据
     * 1、最新数据 2、历史数据
     */
    private Integer latest;


    /**
     * 基础评价表id
     */
    private String evaluateResultId;

    /**
     * 评价数据的来源 1、手动新增 2、自动发起 3、生成年度评价 4、对接旧版考核数据 5、线下导入 6、历史数据手动新增
     */
    private Integer sourceType;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目阶段 前期：01、施工：02、验收：03
     */
    private String projectStage;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同类型
     */
    private String conType;

    /**
     * 评价类型：季度（01） 、阶段（02）、年度（03）、最终（04）、月度（05）、年度初评（06）、最终初评（07）
     */
    private String evaluateType;

    /**
     * 年度、最终评价的评价方式：1、直接评价（使用指标体系） 2、综合评价（算术平均分等规则）
     */
    private Integer evaluationFinalType;

    /**
     * 是否综合评价：1、是 2、否
     */
    private Integer comprehensiveEvaluation;


    /**
     * 分开评价依据支撑
     */
    private String supportingMaterials;

    /**
     * 履约单位id
     */
    private String peCompanyId;


    /**
     * 代建子合同
     */
    private String peSubContract;


    /**
     * 评价合同类型  02 ：代建子合同 01：非代建子合同
     */
    private String peContractCategory;


    /**
     * 项目经理
     */
    private String projectManager;

    /**
     * 项目经理电话
     */
    private String projectManagerTel;

    /**
     * 项目经理身份证号
     */
    private String projectManagerIdCard;

    /**
     * 企业负责人
     */
    private String comPrincipal;

    /**
     * 企业负责人手机
     */
    private String comPrincipalTel;

    /**
     * 合同履约开始时间
     */
    private Date conStartDate;

    /**
     * 合同履约结束时间
     */
    private Date conEndDate;

    /**
     * 考核开始时间
     */
    private Date evaluateStartDate;

    /**
     * 考核结束时间
     */
    private Date evaluateEndDate;

    /**
     * 工期
     */
    private String duration;

    /**
     * 实际开工日期
     */
    private Date actualStartDate;

    /**
     * 汇总状态 0未汇总 1、汇总审核中 2、已汇总 3、汇总退回
     */
    private Integer packageStatus;

    /**
     * 上会状态 0未上会 1、无需上会 2、已上会 3、上会退回 4、提请上会
     */
    private Integer meetingStatus;

    /**
     * 评价年度
     */
    private Integer evaluationYear;

    /**
     * 年度评价、最终评价 算评价分用到的季度阶段的考核id
     */
    private String quarterStageObjectContractId;

    /**
     * 评价季度
     */
    private Integer evaluationQuarter;
    /**
     * 评价月份
     */
    private Integer evaluationMonth;
    /**
     * 评价阶段id
     */
    private String evaluationStageId;

    /**
     * 评价阶段
     */
    private String evaluationStage;

    /**
     * 是否参评  0 否 1 是
     */
    private String peJoin;
    /**
     * 业务类型
     * 1、项目级平台考核
     * 2、项目智能考核
     */
    private Integer businessType;

    /**
     * 流程状态
     */
    private Integer flowStatus;

    /**
     * 审核时间
     */
    private Date reviewTime;

    //创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建人姓名
     */
    private String createName;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 删除状态：1正常 2删除
     */
    private Integer delFlag;
    /**
     * 状态：1 有效，2 禁用
     */
    private Integer status;


}
