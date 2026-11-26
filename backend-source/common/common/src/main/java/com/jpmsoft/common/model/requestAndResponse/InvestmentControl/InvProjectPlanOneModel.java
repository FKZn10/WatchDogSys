package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金计划
 */
public class InvProjectPlanOneModel extends BaseModeRequest {

    private String ippoId;
    private String pId;
    private String ippoYear;
    private String ippoDocNumber;
    private BigDecimal ippoReleaseFunds;
    private BigDecimal ippoAnnualReleaseFunds;
    private BigDecimal ippoAnnualAccumFund;
    private String ippoFile;
    private String ippoCreateUid;
    private String ippoCreateUname;
    private Date ippoCreateTime;

    private String pName;
    private String pNameHistroy;
    private String pCode;
    private String ippoFileIds;
    private String pProjectCode;
    private BigDecimal pCalculateRoughlyAmount;


    public String getIppoId() {
        return ippoId;
    }

    public void setIppoId(String ippoId) {
        this.ippoId = ippoId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getIppoYear() {
        return ippoYear;
    }

    public void setIppoYear(String ippoYear) {
        this.ippoYear = ippoYear;
    }

    public String getIppoDocNumber() {
        return ippoDocNumber;
    }

    public void setIppoDocNumber(String ippoDocNumber) {
        this.ippoDocNumber = ippoDocNumber;
    }

    public BigDecimal getIppoReleaseFunds() {
        return ippoReleaseFunds;
    }

    public void setIppoReleaseFunds(BigDecimal ippoReleaseFunds) {
        this.ippoReleaseFunds = ippoReleaseFunds;
    }

    public BigDecimal getIppoAnnualReleaseFunds() {
        return ippoAnnualReleaseFunds;
    }

    public void setIppoAnnualReleaseFunds(BigDecimal ippoAnnualReleaseFunds) {
        this.ippoAnnualReleaseFunds = ippoAnnualReleaseFunds;
    }

    public BigDecimal getIppoAnnualAccumFund() {
        return ippoAnnualAccumFund;
    }

    public void setIppoAnnualAccumFund(BigDecimal ippoAnnualAccumFund) {
        this.ippoAnnualAccumFund = ippoAnnualAccumFund;
    }

    public String getIppoFile() {
        return ippoFile;
    }

    public void setIppoFile(String ippoFile) {
        this.ippoFile = ippoFile;
    }

    public String getIppoCreateUid() {
        return ippoCreateUid;
    }

    public void setIppoCreateUid(String ippoCreateUid) {
        this.ippoCreateUid = ippoCreateUid;
    }

    public String getIppoCreateUname() {
        return ippoCreateUname;
    }

    public void setIppoCreateUname(String ippoCreateUname) {
        this.ippoCreateUname = ippoCreateUname;
    }

    public Date getIppoCreateTime() {
        return ippoCreateTime;
    }

    public void setIppoCreateTime(Date ippoCreateTime) {
        this.ippoCreateTime = ippoCreateTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpNameHistroy() {
        return pNameHistroy;
    }

    public void setpNameHistroy(String pNameHistroy) {
        this.pNameHistroy = pNameHistroy;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getIppoFileIds() {
        return ippoFileIds;
    }

    public void setIppoFileIds(String ippoFileIds) {
        this.ippoFileIds = ippoFileIds;
    }

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public BigDecimal getpCalculateRoughlyAmount() {
        return pCalculateRoughlyAmount;
    }

    public void setpCalculateRoughlyAmount(BigDecimal pCalculateRoughlyAmount) {
        this.pCalculateRoughlyAmount = pCalculateRoughlyAmount;
    }
}
