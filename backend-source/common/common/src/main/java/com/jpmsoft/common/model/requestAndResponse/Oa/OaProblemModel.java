package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;
import java.util.List;

/**
 * 项目问题登记表
 */

public class OaProblemModel extends ProjectCommonRes {
    private String pmId;
    private String pId;
    private String secId;
    private String conId;
    private String pmTitle;
    private String pmRemark;
    private String pmCreateUid;
    private String pmCreateUname;
    private Date pmCreateTime;
    private Integer pmState;
    private Date pmStateTime;
    private String pmCode;


    private String secName;
    private String conName;
    private List<OaProblemDtlModel> oaProblemDtls;

    /**
     * 交办人员id
     */
    private String assignedPersonId;

    /**
     * 交办人员姓名
     */
    private String assignedPersonName;

    /**
     *部门id
     */
    private String assignedDepartId;

    /**
     *部门名称
     */
    private String assignedDepartName;

    public String getAssignedPersonId() {
        return assignedPersonId;
    }

    public void setAssignedPersonId(String assignedPersonId) {
        this.assignedPersonId = assignedPersonId;
    }

    public String getAssignedPersonName() {
        return assignedPersonName;
    }

    public void setAssignedPersonName(String assignedPersonName) {
        this.assignedPersonName = assignedPersonName;
    }

    public String getAssignedDepartId() {
        return assignedDepartId;
    }

    public void setAssignedDepartId(String assignedDepartId) {
        this.assignedDepartId = assignedDepartId;
    }

    public String getAssignedDepartName() {
        return assignedDepartName;
    }

    public void setAssignedDepartName(String assignedDepartName) {
        this.assignedDepartName = assignedDepartName;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getPmTitle() {
        return pmTitle;
    }

    public void setPmTitle(String pmTitle) {
        this.pmTitle = pmTitle;
    }

    public String getPmRemark() {
        return pmRemark;
    }

    public void setPmRemark(String pmRemark) {
        this.pmRemark = pmRemark;
    }

    public String getPmCreateUid() {
        return pmCreateUid;
    }

    public void setPmCreateUid(String pmCreateUid) {
        this.pmCreateUid = pmCreateUid;
    }

    public String getPmCreateUname() {
        return pmCreateUname;
    }

    public void setPmCreateUname(String pmCreateUname) {
        this.pmCreateUname = pmCreateUname;
    }

    public Date getPmCreateTime() {
        return pmCreateTime;
    }

    public void setPmCreateTime(Date pmCreateTime) {
        this.pmCreateTime = pmCreateTime;
    }

    public Integer getPmState() {
        return pmState;
    }

    public void setPmState(Integer pmState) {
        this.pmState = pmState;
    }

    public Date getPmStateTime() {
        return pmStateTime;
    }

    public void setPmStateTime(Date pmStateTime) {
        this.pmStateTime = pmStateTime;
    }

    public String getPmCode() {
        return pmCode;
    }

    public void setPmCode(String pmCode) {
        this.pmCode = pmCode;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public List<OaProblemDtlModel> getOaProblemDtls() {
        return oaProblemDtls;
    }

    public void setOaProblemDtls(List<OaProblemDtlModel> oaProblemDtls) {
        this.oaProblemDtls = oaProblemDtls;
    }
}
