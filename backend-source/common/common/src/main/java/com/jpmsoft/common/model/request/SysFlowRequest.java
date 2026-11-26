package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 已部署流程
 */
public class SysFlowRequest extends BaseModeRequest {

    private String flowname;
    private String flowSolutionName;
    private String flowCompanyName;
    private String tName;
    private String pName;
    /**
     * 根据当前用户组装机构过滤
     */
    private boolean filterOwnerDep;

    public String getFlowname() {
        return flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
    }

    public String getFlowSolutionName() {
        return flowSolutionName;
    }

    public void setFlowSolutionName(String flowSolutionName) {
        this.flowSolutionName = flowSolutionName;
    }

    public String getFlowCompanyName() {
        return flowCompanyName;
    }

    public void setFlowCompanyName(String flowCompanyName) {
        this.flowCompanyName = flowCompanyName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public boolean isFilterOwnerDep() {
        return filterOwnerDep;
    }

    public void setFilterOwnerDep(boolean filterOwnerDep) {
        this.filterOwnerDep = filterOwnerDep;
    }
}
