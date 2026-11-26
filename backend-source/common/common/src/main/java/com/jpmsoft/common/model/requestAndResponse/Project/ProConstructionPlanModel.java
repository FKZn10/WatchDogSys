package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目立项资金来源
 */
public class ProConstructionPlanModel extends BaseFileRequest {

    private String pcpId;
    private String pcpFileName;
    private Integer pcpYear;
    private String pcpCreateUid;
    private String pcpCreateUname;
    private Date pcpCreateTime;
    private Integer pcpState;
    private Date pcpStateTime;
    private String pcpRemarks;

    /**
     * 清单数
     */
    private int cplTotal;
    /**
     * 清单计划总投资累计
     */
    private BigDecimal cplInvSum;

    private List<ProConstructionPlanListModel> proConstPlanListModels;

    public String getPcpId() {
        return pcpId;
    }

    public void setPcpId(String pcpId) {
        this.pcpId = pcpId;
    }

    public String getPcpFileName() {
        return pcpFileName;
    }

    public void setPcpFileName(String pcpFileName) {
        this.pcpFileName = pcpFileName;
    }

    public Integer getPcpYear() {
        return pcpYear;
    }

    public void setPcpYear(Integer pcpYear) {
        this.pcpYear = pcpYear;
    }

    public String getPcpCreateUid() {
        return pcpCreateUid;
    }

    public void setPcpCreateUid(String pcpCreateUid) {
        this.pcpCreateUid = pcpCreateUid;
    }

    public String getPcpCreateUname() {
        return pcpCreateUname;
    }

    public void setPcpCreateUname(String pcpCreateUname) {
        this.pcpCreateUname = pcpCreateUname;
    }

    public Date getPcpCreateTime() {
        return pcpCreateTime;
    }

    public void setPcpCreateTime(Date pcpCreateTime) {
        this.pcpCreateTime = pcpCreateTime;
    }

    public Integer getPcpState() {
        return pcpState;
    }

    public void setPcpState(Integer pcpState) {
        this.pcpState = pcpState;
    }

    public Date getPcpStateTime() {
        return pcpStateTime;
    }

    public void setPcpStateTime(Date pcpStateTime) {
        this.pcpStateTime = pcpStateTime;
    }

    public String getPcpRemarks() {
        return pcpRemarks;
    }

    public void setPcpRemarks(String pcpRemarks) {
        this.pcpRemarks = pcpRemarks;
    }

    public int getCplTotal() {
        return cplTotal;
    }

    public void setCplTotal(int cplTotal) {
        this.cplTotal = cplTotal;
    }

    public BigDecimal getCplInvSum() {
        return cplInvSum;
    }

    public void setCplInvSum(BigDecimal cplInvSum) {
        this.cplInvSum = cplInvSum;
    }

    public List<ProConstructionPlanListModel> getProConstPlanListModels() {
        return proConstPlanListModels;
    }

    public void setProConstPlanListModels(List<ProConstructionPlanListModel> proConstPlanListModels) {
        this.proConstPlanListModels = proConstPlanListModels;
    }
}
