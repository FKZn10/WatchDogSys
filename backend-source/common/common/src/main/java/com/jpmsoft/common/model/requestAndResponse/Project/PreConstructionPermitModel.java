package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 开工许可证
 */
public class PreConstructionPermitModel extends ProjectCommonRes {

    private String cpId;
    private String conId;
    private String cpProjectAddress;
    private BigDecimal cpContractPrice;
    private String cpFixedPrice;
    private Date cpStartTime;
    private Date cpEndTime;
    private String cpOverview;
    private String cpCreateCid;
    private String cpCreateUid;
    private String cpCreateUname;
    private Date cpCreateTime;
    private Integer cpState;
    private Date cpStateTime;

    //附件relationId
    private String cpProApprDoc;
    private String cpCommApp;
    private String cpHeadquarters;
    private String cpApprovalDoc;
    private String cpConsPermit;
    private String cpQualitySafety;
    private String cpFundProve;
    private String cpHandleProve;
    private String cpContractFile;
    private String cpPerfoPayProve;
    private String cpCompletionProve;
    private String cpPayrollAccount;
    private String cpBillQuantities;
    private Float cpLongitude;
    private Float cpLatitude;


    //附件ID
    private String cpProApprDocIds;
    private String cpCommAppIds;
    private String cpHeadquartersIds;
    private String cpApprovalDocIds;
    private String cpConsPermitIds;
    private String cpQualitySafetyIds;
    private String cpFundProveIds;
    private String cpHandleProveIds;
    private String cpContractFileIds;
    private String cpPerfoPayProveIds;
    private String cpCompletionProveIds;
    private String cpPayrollAccountIds;
    private String cpBillQuantitiesIds;

    private String conName;
    private String cpCreateCName;
    private String cpCreateCNameHistory;
    private List<String> cpContractPrices;
    private List<String> cpStartTimes;
    private List<String> cpEndTimes;
    private List<String> cpCreateTimes;
    private List<PreConstructionPermitUnitModel> preConstructionPermitUnits;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }


    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCpProjectAddress() {
        return cpProjectAddress;
    }

    public void setCpProjectAddress(String cpProjectAddress) {
        this.cpProjectAddress = cpProjectAddress;
    }

    public BigDecimal getCpContractPrice() {
        return cpContractPrice;
    }

    public void setCpContractPrice(BigDecimal cpContractPrice) {
        this.cpContractPrice = cpContractPrice;
    }

    public String getCpFixedPrice() {
        return cpFixedPrice;
    }

    public void setCpFixedPrice(String cpFixedPrice) {
        this.cpFixedPrice = cpFixedPrice;
    }

    public Date getCpStartTime() {
        return cpStartTime;
    }

    public void setCpStartTime(Date cpStartTime) {
        this.cpStartTime = cpStartTime;
    }

    public Date getCpEndTime() {
        return cpEndTime;
    }

    public void setCpEndTime(Date cpEndTime) {
        this.cpEndTime = cpEndTime;
    }

    public String getCpOverview() {
        return cpOverview;
    }

    public void setCpOverview(String cpOverview) {
        this.cpOverview = cpOverview;
    }

    public String getCpCreateCid() {
        return cpCreateCid;
    }

    public void setCpCreateCid(String cpCreateCid) {
        this.cpCreateCid = cpCreateCid;
    }

    public String getCpCreateUid() {
        return cpCreateUid;
    }

    public void setCpCreateUid(String cpCreateUid) {
        this.cpCreateUid = cpCreateUid;
    }

    public String getCpCreateUname() {
        return cpCreateUname;
    }

    public void setCpCreateUname(String cpCreateUname) {
        this.cpCreateUname = cpCreateUname;
    }

    public Date getCpCreateTime() {
        return cpCreateTime;
    }

    public void setCpCreateTime(Date cpCreateTime) {
        this.cpCreateTime = cpCreateTime;
    }

    public Integer getCpState() {
        return cpState;
    }

    public void setCpState(Integer cpState) {
        this.cpState = cpState;
    }

    public Date getCpStateTime() {
        return cpStateTime;
    }

    public void setCpStateTime(Date cpStateTime) {
        this.cpStateTime = cpStateTime;
    }

    public String getCpProApprDoc() {
        return cpProApprDoc;
    }

    public void setCpProApprDoc(String cpProApprDoc) {
        this.cpProApprDoc = cpProApprDoc;
    }

    public String getCpCommApp() {
        return cpCommApp;
    }

    public void setCpCommApp(String cpCommApp) {
        this.cpCommApp = cpCommApp;
    }

    public String getCpHeadquarters() {
        return cpHeadquarters;
    }

    public void setCpHeadquarters(String cpHeadquarters) {
        this.cpHeadquarters = cpHeadquarters;
    }

    public String getCpApprovalDoc() {
        return cpApprovalDoc;
    }

    public void setCpApprovalDoc(String cpApprovalDoc) {
        this.cpApprovalDoc = cpApprovalDoc;
    }

    public String getCpConsPermit() {
        return cpConsPermit;
    }

    public void setCpConsPermit(String cpConsPermit) {
        this.cpConsPermit = cpConsPermit;
    }

    public String getCpQualitySafety() {
        return cpQualitySafety;
    }

    public void setCpQualitySafety(String cpQualitySafety) {
        this.cpQualitySafety = cpQualitySafety;
    }

    public String getCpFundProve() {
        return cpFundProve;
    }

    public void setCpFundProve(String cpFundProve) {
        this.cpFundProve = cpFundProve;
    }

    public String getCpHandleProve() {
        return cpHandleProve;
    }

    public void setCpHandleProve(String cpHandleProve) {
        this.cpHandleProve = cpHandleProve;
    }

    public String getCpContractFile() {
        return cpContractFile;
    }

    public void setCpContractFile(String cpContractFile) {
        this.cpContractFile = cpContractFile;
    }

    public String getCpPerfoPayProve() {
        return cpPerfoPayProve;
    }

    public void setCpPerfoPayProve(String cpPerfoPayProve) {
        this.cpPerfoPayProve = cpPerfoPayProve;
    }

    public String getCpCompletionProve() {
        return cpCompletionProve;
    }

    public void setCpCompletionProve(String cpCompletionProve) {
        this.cpCompletionProve = cpCompletionProve;
    }

    public String getCpPayrollAccount() {
        return cpPayrollAccount;
    }

    public void setCpPayrollAccount(String cpPayrollAccount) {
        this.cpPayrollAccount = cpPayrollAccount;
    }

    public Float getCpLongitude() {
        return cpLongitude;
    }

    public void setCpLongitude(Float cpLongitude) {
        this.cpLongitude = cpLongitude;
    }

    public Float getCpLatitude() {
        return cpLatitude;
    }

    public void setCpLatitude(Float cpLatitude) {
        this.cpLatitude = cpLatitude;
    }

    public String getCpProApprDocIds() {
        return cpProApprDocIds;
    }

    public void setCpProApprDocIds(String cpProApprDocIds) {
        this.cpProApprDocIds = cpProApprDocIds;
    }

    public String getCpCommAppIds() {
        return cpCommAppIds;
    }

    public void setCpCommAppIds(String cpCommAppIds) {
        this.cpCommAppIds = cpCommAppIds;
    }

    public String getCpHeadquartersIds() {
        return cpHeadquartersIds;
    }

    public void setCpHeadquartersIds(String cpHeadquartersIds) {
        this.cpHeadquartersIds = cpHeadquartersIds;
    }

    public String getCpApprovalDocIds() {
        return cpApprovalDocIds;
    }

    public void setCpApprovalDocIds(String cpApprovalDocIds) {
        this.cpApprovalDocIds = cpApprovalDocIds;
    }

    public String getCpConsPermitIds() {
        return cpConsPermitIds;
    }

    public void setCpConsPermitIds(String cpConsPermitIds) {
        this.cpConsPermitIds = cpConsPermitIds;
    }

    public String getCpQualitySafetyIds() {
        return cpQualitySafetyIds;
    }

    public void setCpQualitySafetyIds(String cpQualitySafetyIds) {
        this.cpQualitySafetyIds = cpQualitySafetyIds;
    }

    public String getCpFundProveIds() {
        return cpFundProveIds;
    }

    public void setCpFundProveIds(String cpFundProveIds) {
        this.cpFundProveIds = cpFundProveIds;
    }

    public String getCpHandleProveIds() {
        return cpHandleProveIds;
    }

    public void setCpHandleProveIds(String cpHandleProveIds) {
        this.cpHandleProveIds = cpHandleProveIds;
    }

    public String getCpContractFileIds() {
        return cpContractFileIds;
    }

    public void setCpContractFileIds(String cpContractFileIds) {
        this.cpContractFileIds = cpContractFileIds;
    }

    public String getCpPerfoPayProveIds() {
        return cpPerfoPayProveIds;
    }

    public void setCpPerfoPayProveIds(String cpPerfoPayProveIds) {
        this.cpPerfoPayProveIds = cpPerfoPayProveIds;
    }

    public String getCpCompletionProveIds() {
        return cpCompletionProveIds;
    }

    public void setCpCompletionProveIds(String cpCompletionProveIds) {
        this.cpCompletionProveIds = cpCompletionProveIds;
    }

    public String getCpPayrollAccountIds() {
        return cpPayrollAccountIds;
    }

    public void setCpPayrollAccountIds(String cpPayrollAccountIds) {
        this.cpPayrollAccountIds = cpPayrollAccountIds;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCpCreateCName() {
        return cpCreateCName;
    }

    public void setCpCreateCName(String cpCreateCName) {
        this.cpCreateCName = cpCreateCName;
    }

    public String getCpCreateCNameHistory() {
        return cpCreateCNameHistory;
    }

    public void setCpCreateCNameHistory(String cpCreateCNameHistory) {
        this.cpCreateCNameHistory = cpCreateCNameHistory;
    }

    public List<String> getCpContractPrices() {
        return cpContractPrices;
    }

    public void setCpContractPrices(List<String> cpContractPrices) {
        this.cpContractPrices = cpContractPrices;
    }

    public List<String> getCpStartTimes() {
        return cpStartTimes;
    }

    public void setCpStartTimes(List<String> cpStartTimes) {
        this.cpStartTimes = cpStartTimes;
    }

    public List<String> getCpEndTimes() {
        return cpEndTimes;
    }

    public void setCpEndTimes(List<String> cpEndTimes) {
        this.cpEndTimes = cpEndTimes;
    }

    public List<String> getCpCreateTimes() {
        return cpCreateTimes;
    }

    public void setCpCreateTimes(List<String> cpCreateTimes) {
        this.cpCreateTimes = cpCreateTimes;
    }

    public List<PreConstructionPermitUnitModel> getPreConstructionPermitUnits() {
        return preConstructionPermitUnits;
    }

    public void setPreConstructionPermitUnits(List<PreConstructionPermitUnitModel> preConstructionPermitUnits) {
        this.preConstructionPermitUnits = preConstructionPermitUnits;
    }

    public String getCpBillQuantitiesIds() {
        return cpBillQuantitiesIds;
    }

    public void setCpBillQuantitiesIds(String cpBillQuantitiesIds) {
        this.cpBillQuantitiesIds = cpBillQuantitiesIds;
    }

    public String getCpBillQuantities() {
        return cpBillQuantities;
    }

    public void setCpBillQuantities(String cpBillQuantities) {
        this.cpBillQuantities = cpBillQuantities;
    }
}
