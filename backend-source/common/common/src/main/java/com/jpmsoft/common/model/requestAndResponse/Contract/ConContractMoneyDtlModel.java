package com.jpmsoft.common.model.requestAndResponse.Contract;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同金额明细
 */

public class ConContractMoneyDtlModel {

    private String cmdId;
    private String conId;
    private String cmdName;
    private BigDecimal cmdMoney;
    private String cmdRemark;
    private String cmdParentId;
    private Integer cmdSort;
    private Date cmdCreateTime;

    private List<ConContractMoneyDtlModel> subMoneyDtlModels;


    public String getCmdId() {
        return cmdId;
    }

    public void setCmdId(String cmdId) {
        this.cmdId = cmdId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public BigDecimal getCmdMoney() {
        return cmdMoney;
    }

    public void setCmdMoney(BigDecimal cmdMoney) {
        this.cmdMoney = cmdMoney;
    }

    public String getCmdRemark() {
        return cmdRemark;
    }

    public void setCmdRemark(String cmdRemark) {
        this.cmdRemark = cmdRemark;
    }

    public String getCmdParentId() {
        return cmdParentId;
    }

    public void setCmdParentId(String cmdParentId) {
        this.cmdParentId = cmdParentId;
    }

    public Integer getCmdSort() {
        return cmdSort;
    }

    public void setCmdSort(Integer cmdSort) {
        this.cmdSort = cmdSort;
    }

    public Date getCmdCreateTime() {
        return cmdCreateTime;
    }

    public void setCmdCreateTime(Date cmdCreateTime) {
        this.cmdCreateTime = cmdCreateTime;
    }

    public List<ConContractMoneyDtlModel> getSubMoneyDtlModels() {
        return subMoneyDtlModels;
    }

    public void setSubMoneyDtlModels(List<ConContractMoneyDtlModel> subMoneyDtlModels) {
        this.subMoneyDtlModels = subMoneyDtlModels;
    }
}
