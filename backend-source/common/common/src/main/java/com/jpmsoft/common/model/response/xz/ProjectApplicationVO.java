package com.jpmsoft.common.model.response.xz;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 11:40
 * @Description:
 * @Version: 1.0
 */

@Data
public class ProjectApplicationVO implements Serializable {

    private static final long serialVersionUID = 4111717895908413823L;

    private String id;

    /**
     * 申报单位id
     */
    private String applicationCompanyId;

    private String applicationCompanyName;

    /**
     * 计划年度
     */
    private String planYear;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 申报人联系电话
     */
    private String applicationUserPhone;

    /**
     * 项目类型(字典编码：JPMsoft_XZ_XMLB)
     */
    private String projectType;

    /**
     * 项目类别(字典编码：JPMsoft_XZ_XMLB)
     */
    private String projectCategory;

    /**
     * 申报编号(编码规则：ProjectApplicationNumCode)
     */
    private String applicationNum;

    /**
     * 项目属性(字典编码：JPMsoft_XZ_XMSX)
     */
    private String projectAttribute;

    /**
     * 项目负责人
     */
    private String projectManager;

    /**
     * 项目负责人联系电话
     */
    private String projectManagerPhone;

    /**
     * 项目总预算(单位:元)
     */
    private BigDecimal generalBudget;

    /**
     * 政府采购预算(单位:元)
     */
    private BigDecimal governmentBudget;

    /**
     * 非政府采购预算(单位:元)
     */
    private BigDecimal otherBudget;

    /**
     * 项目总金额(单位:元)
     */
    private BigDecimal generalAmount;

    /**
     * 政府采购金额(单位:元)
     */
    private BigDecimal governmentAmount;

    /**
     * 非政府采购金额(单位:元)
     */
    private BigDecimal otherAmount;

    /**
     * 计划开始年月 yyyy-mm
     */
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date planStartDate;

    /**
     * 计划结束年月 yyyy-mm
     */
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date planEndDate;

    /**
     * 项目简介
     */
    private String projectIntroduction;

    /**
     * 建设目标
     */
    private String projectTarget;

    /**
     * 初审评分
     */
    private Integer preApproveScore;

    /**
     * 初审意见
     */
    private String preApproveOpinion;

    /**
     * 终审评分
     */
    private Integer finalApproveScore;

    /**
     * 终审意见
     */
    private String finalApproveOpinion;

    /**
     * 项目申报书附件
     */
    private String applicationFileRelationId;

    /**
     * 项目初审附件
     */
    private String approveFileRelationId;

    /**
     *项目终审附件
     */
    private String finalApproveFileRelationId;

    /**
     * 归口部门
     */
    private String relevantDepart;

    /**
     *归口部门id
     */
    private String relevantDepartId;

    /**
     * 任务状态：save：未启动; start-待初审; relevant-待评审;finance-已评审; confirm-已入库; back-退回
     */
    private String taskStatus;

    private String approveStatus;

    private String createId;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

}
