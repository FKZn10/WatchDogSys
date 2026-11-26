package com.jpmsoft.projectProducer.pojo.vo.constructionProcedure;

import java.io.Serializable;

/**
 * 统计项目程序与详情总数
 */
public class StatisticalProCpAndDtlVo implements Serializable {

    private static final long serialVersionUID = -5135521077570796006L;
    /**
     * 项目数量
     */
    private Integer projectCount;
    /**
     * 项目程序数量
     */
    private Integer cpCount;
    /**
     * 建设程序数量
     */
    private Integer cpDtlCount;
    /**
     * 已办理总数
     */
    private Integer completeCpDtlCount;
    /**
     * 办理人总数
     */
    private Integer cpDtlCreateUserCount;

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }

    public Integer getCpCount() {
        return cpCount;
    }

    public void setCpCount(Integer cpCount) {
        this.cpCount = cpCount;
    }

    public Integer getCpDtlCount() {
        return cpDtlCount;
    }

    public void setCpDtlCount(Integer cpDtlCount) {
        this.cpDtlCount = cpDtlCount;
    }

    public Integer getCompleteCpDtlCount() {
        return completeCpDtlCount;
    }

    public void setCompleteCpDtlCount(Integer completeCpDtlCount) {
        this.completeCpDtlCount = completeCpDtlCount;
    }

    public Integer getCpDtlCreateUserCount() {
        return cpDtlCreateUserCount;
    }

    public void setCpDtlCreateUserCount(Integer cpDtlCreateUserCount) {
        this.cpDtlCreateUserCount = cpDtlCreateUserCount;
    }
}
