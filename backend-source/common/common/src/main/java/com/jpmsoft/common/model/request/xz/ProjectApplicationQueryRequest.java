package com.jpmsoft.common.model.request.xz;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/7/17 10:50
 * @Description:
 * @Version: 1.0
 */
@Data
public class ProjectApplicationQueryRequest implements Serializable {

    private static final long serialVersionUID = -1590264997818262096L;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 申报单位id
     */
    private String applicationCompanyId;

    /**
     * 登录人部门id,多个用逗号隔开
     */
    private String createDepartId;

    private String applicationCompanyName;

    /**
     * 计划年度
     */
    private String planYear;

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
     * 项目负责人
     */
    private String projectManager;

    /**
     * 任务状态：save：未启动; start-待初审; relevant-待评审;finance-已评审; confirm-已入库; back-退回
     */
    private String taskStatus;

    private String sortFiled;

    private String sortType;

    private String createId;

    /**
     * 页面类型：01-项目申报；02-项目评审；03-项目储备
     */
    private String pageType;

    private Integer pageNum;

    private Integer pageSize;
}
