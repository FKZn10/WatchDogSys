package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调整事项
 */
public class InvAdjustmentRequest extends BaseFileRequest {

    private String iaId;
    private String pId;
    private String conId;
    private String tId;
    private String iaType;
    private String remark;
    private BigDecimal totalAmount;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private List<InvAdjustmentDtlModel> invAdjustmentDtls;

    public String getIaId() {
        return iaId;
    }

    public void setIaId(String iaId) {
        this.iaId = iaId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    @Override
    public String gettId() {
        return tId;
    }

    @Override
    public void settId(String tId) {
        this.tId = tId;
    }

    public String getIaType() {
        return iaType;
    }

    public void setIaType(String iaType) {
        this.iaType = iaType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public List<InvAdjustmentDtlModel> getInvAdjustmentDtls() {
        return invAdjustmentDtls;
    }

    public void setInvAdjustmentDtls(List<InvAdjustmentDtlModel> invAdjustmentDtls) {
        this.invAdjustmentDtls = invAdjustmentDtls;
    }
}
