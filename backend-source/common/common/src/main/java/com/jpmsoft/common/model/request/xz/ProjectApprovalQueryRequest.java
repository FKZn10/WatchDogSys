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
public class ProjectApprovalQueryRequest implements Serializable {

    private static final long serialVersionUID = -1590264997818262096L;

    /**
     * 项目名称
     */
    private String projectName;

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
     * 项目申报类型：government-政府采购金额；other-非政府采购金额
     */
    private String projectAmountType;

    private String sortFiled;

    private String sortType;


    private Integer pageNum;

    private Integer pageSize;
}
