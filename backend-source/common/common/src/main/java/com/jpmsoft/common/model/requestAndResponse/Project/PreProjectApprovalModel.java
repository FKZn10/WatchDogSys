package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.Date;

@Data
public class PreProjectApprovalModel extends BaseFileDto {
    private String id;
    //申报单位
    private String approvalCompanyId;
    private String approvalCompanyName;
    //计划年度
    private Integer approvalPlanYear;
    //申报人
    private String approvalUserId;
    private String approvalUserName;
    //项目名称
    private String approvalProjectName;
    //项目类型
    private String approvalProjectType;
    //项目类别
    private String approvalProjectCategory;
    //申报编号
    private String approvalProjectCode;
    //项目属性
    private String approvalProjectProperty;
    //项目负责人
    private String approvalProjectLeader;
    private String approvalProjectLeaderName;
    //项目负责人电话
    private String approvalProjectLeaderPhone;
    //项目预算
    private Double approvalProjectBudget;
    private Double approvalProjectBudgetGov;
    private Double approvalProjectBudgetNotGov;
    //计划建设周期
    private Date approvalProjectStartTime;
    private Date approvalProjectEndTime;
    //项目简介
    private String approvalProjectIntroduction;
    //项目目标
    private String approvalProjectTarget;
    //项目申报书附件
    private String approvalProjectFile;
    //项目评审分数
    private Double approvalProjectScore;
    //初审附件
    private String approvalProjectScoreFile;
    /**
     * ①　0 未启动：未提交的
     * ②　1 待初审：已提交待归口部门审核的
     * ③　2 待评审：归口部门已初评完成通过待财务财务资产管理处组织评审的
     * ④  3 已评审：财务资产管理处组织线下评审，完成项目评审结
     * ⑤　4 已入库：财务资产管理处已经评审且通过入库的（台账上不显示）
     * ⑥  5 退回：财务资产管理处评审未通过的（项目数据退回‘项目申报’，评审台账不显示）
     */
    private Integer approvalProjectState;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
}
