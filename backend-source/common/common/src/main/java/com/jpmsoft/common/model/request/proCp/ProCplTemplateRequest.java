package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 建设程序项目模板
 */
public class ProCplTemplateRequest extends BaseModeRequest {

    private String cpltId;
    private String cpltName;
    private String cpltPtype;
    private Date cpltCreateTime;
    private String cpltCreateUid;
    private String cpltCreateUname;
    private String cpltRemark;
    private Date cpltEditTime;
    private String cpltState;
    private List<ProjectCpLibraryRequset> proCpLibrarys;

    public String getCpltId() {
        return cpltId;
    }

    public void setCpltId(String cpltId) {
        this.cpltId = cpltId;
    }

    public String getCpltName() {
        return cpltName;
    }

    public void setCpltName(String cpltName) {
        this.cpltName = cpltName;
    }

    public String getCpltPtype() {
        return cpltPtype;
    }

    public void setCpltPtype(String cpltPtype) {
        this.cpltPtype = cpltPtype;
    }

    public Date getCpltCreateTime() {
        return cpltCreateTime;
    }

    public void setCpltCreateTime(Date cpltCreateTime) {
        this.cpltCreateTime = cpltCreateTime;
    }

    public String getCpltCreateUid() {
        return cpltCreateUid;
    }

    public void setCpltCreateUid(String cpltCreateUid) {
        this.cpltCreateUid = cpltCreateUid;
    }

    public String getCpltCreateUname() {
        return cpltCreateUname;
    }

    public void setCpltCreateUname(String cpltCreateUname) {
        this.cpltCreateUname = cpltCreateUname;
    }

    public String getCpltRemark() {
        return cpltRemark;
    }

    public void setCpltRemark(String cpltRemark) {
        this.cpltRemark = cpltRemark;
    }

    public Date getCpltEditTime() {
        return cpltEditTime;
    }

    public void setCpltEditTime(Date cpltEditTime) {
        this.cpltEditTime = cpltEditTime;
    }

    public List<ProjectCpLibraryRequset> getProCpLibrarys() {
        return proCpLibrarys;
    }

    public void setProCpLibrarys(List<ProjectCpLibraryRequset> proCpLibrarys) {
        this.proCpLibrarys = proCpLibrarys;
    }

    public String getCpltState() {
        return cpltState;
    }

    public void setCpltState(String cpltState) {
        this.cpltState = cpltState;
    }
}
