package com.jpmsoft.common.model.request.MeEnter;


import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 材料设备进场报审清单
 */
public class MeEnterApplyDtlRequest extends BaseModeRequest {
    private String eadId;
    private String metId;
    private String eaId;
    private String eadStandardType;
    private Float eadCount;
    private String eadBrandId;
    private String eadBrandName;
    private String eadManufacturer;
    private String eadSupplierId;
    private String eadSupplierName;
    private String eadSupplyOrderNo;
    private String eadBatchNumber;
    private Date eadSupplyOrderNoDate;
    private Date eadProductDate;
    private String eadProductionAddress;
    private String eadRemark;
    private Date eadCreateTime;
    private String eadUsedPart;
    private String eadAcceptorId;
    private String eadAcceptorName;
    private String eadAcceptAp;
    private Float eadAcceptCount;
    private Integer eadBrandOk;
    private Integer eadCountOk;
    private Integer eadStandardTypeOk;
    private Date eadAcceptTime;
    private String eadAcceptRemark;
    private String eadSupplierCode;
    private String eadSupplierType;
    private String eadUnit;
    /**********************************/
    private String pName;
    private String pId;
    private String conName;
    private String conId;
    private String eaPurchaseContractName;
    private String eaPurchaseContract;
    private String pConstructionUnit;
    private String conPartyBId;
    private String eaState;
    private String eaCode;
    private String eaDate;
    /**
     * 送检状态
     * 0：全部
     * 1：未送检
     * 2: 已送检
     */
    private Integer inspectionStatus;
    /**
     * 使用报审状态
     * 0：全部
     * 1：可使用报审
     */
    private Integer useApplyStatus;

    /**
     * 验货状态
     * 0：全部
     * 1：未验货
     * 2: 已验货
     */
    private Integer enterInspectionStatus;
    private String uaId;
    private Integer munNumber;
    private Integer eaWitness;

    public String getEadId() {
        return eadId;
    }

    public void setEadId(String eadId) {
        this.eadId = eadId;
    }

    public String getMetId() {
        return metId;
    }

    public void setMetId(String metId) {
        this.metId = metId;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getEadStandardType() {
        return eadStandardType;
    }

    public void setEadStandardType(String eadStandardType) {
        this.eadStandardType = eadStandardType;
    }

    public Float getEadCount() {
        return eadCount;
    }

    public void setEadCount(Float eadCount) {
        this.eadCount = eadCount;
    }

    public String getEadBrandId() {
        return eadBrandId;
    }

    public void setEadBrandId(String eadBrandId) {
        this.eadBrandId = eadBrandId;
    }

    public String getEadBrandName() {
        return eadBrandName;
    }

    public void setEadBrandName(String eadBrandName) {
        this.eadBrandName = eadBrandName;
    }

    public String getEadManufacturer() {
        return eadManufacturer;
    }

    public void setEadManufacturer(String eadManufacturer) {
        this.eadManufacturer = eadManufacturer;
    }

    public String getEadSupplierId() {
        return eadSupplierId;
    }

    public void setEadSupplierId(String eadSupplierId) {
        this.eadSupplierId = eadSupplierId;
    }

    public String getEadSupplierName() {
        return eadSupplierName;
    }

    public void setEadSupplierName(String eadSupplierName) {
        this.eadSupplierName = eadSupplierName;
    }

    public String getEadSupplyOrderNo() {
        return eadSupplyOrderNo;
    }

    public void setEadSupplyOrderNo(String eadSupplyOrderNo) {
        this.eadSupplyOrderNo = eadSupplyOrderNo;
    }

    public String getEadBatchNumber() {
        return eadBatchNumber;
    }

    public void setEadBatchNumber(String eadBatchNumber) {
        this.eadBatchNumber = eadBatchNumber;
    }

    public Date getEadSupplyOrderNoDate() {
        return eadSupplyOrderNoDate;
    }

    public void setEadSupplyOrderNoDate(Date eadSupplyOrderNoDate) {
        this.eadSupplyOrderNoDate = eadSupplyOrderNoDate;
    }

    public Date getEadProductDate() {
        return eadProductDate;
    }

    public void setEadProductDate(Date eadProductDate) {
        this.eadProductDate = eadProductDate;
    }

    public String getEadProductionAddress() {
        return eadProductionAddress;
    }

    public void setEadProductionAddress(String eadProductionAddress) {
        this.eadProductionAddress = eadProductionAddress;
    }

    public String getEadRemark() {
        return eadRemark;
    }

    public void setEadRemark(String eadRemark) {
        this.eadRemark = eadRemark;
    }

    public Date getEadCreateTime() {
        return eadCreateTime;
    }

    public void setEadCreateTime(Date eadCreateTime) {
        this.eadCreateTime = eadCreateTime;
    }

    public String getEadUsedPart() {
        return eadUsedPart;
    }

    public void setEadUsedPart(String eadUsedPart) {
        this.eadUsedPart = eadUsedPart;
    }

    public String getEadAcceptorId() {
        return eadAcceptorId;
    }

    public void setEadAcceptorId(String eadAcceptorId) {
        this.eadAcceptorId = eadAcceptorId;
    }

    public String getEadAcceptorName() {
        return eadAcceptorName;
    }

    public void setEadAcceptorName(String eadAcceptorName) {
        this.eadAcceptorName = eadAcceptorName;
    }

    public String getEadAcceptAp() {
        return eadAcceptAp;
    }

    public void setEadAcceptAp(String eadAcceptAp) {
        this.eadAcceptAp = eadAcceptAp;
    }

    public Float getEadAcceptCount() {
        return eadAcceptCount;
    }

    public void setEadAcceptCount(Float eadAcceptCount) {
        this.eadAcceptCount = eadAcceptCount;
    }

    public Integer getEadBrandOk() {
        return eadBrandOk;
    }

    public void setEadBrandOk(Integer eadBrandOk) {
        this.eadBrandOk = eadBrandOk;
    }

    public Integer getEadCountOk() {
        return eadCountOk;
    }

    public void setEadCountOk(Integer eadCountOk) {
        this.eadCountOk = eadCountOk;
    }

    public Integer getEadStandardTypeOk() {
        return eadStandardTypeOk;
    }

    public void setEadStandardTypeOk(Integer eadStandardTypeOk) {
        this.eadStandardTypeOk = eadStandardTypeOk;
    }

    public Date getEadAcceptTime() {
        return eadAcceptTime;
    }

    public void setEadAcceptTime(Date eadAcceptTime) {
        this.eadAcceptTime = eadAcceptTime;
    }

    public String getEadAcceptRemark() {
        return eadAcceptRemark;
    }

    public void setEadAcceptRemark(String eadAcceptRemark) {
        this.eadAcceptRemark = eadAcceptRemark;
    }

    public String getEadSupplierCode() {
        return eadSupplierCode;
    }

    public void setEadSupplierCode(String eadSupplierCode) {
        this.eadSupplierCode = eadSupplierCode;
    }

    public String getEadSupplierType() {
        return eadSupplierType;
    }

    public void setEadSupplierType(String eadSupplierType) {
        this.eadSupplierType = eadSupplierType;
    }

    public String getEadUnit() {
        return eadUnit;
    }

    public void setEadUnit(String eadUnit) {
        this.eadUnit = eadUnit;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getEaPurchaseContractName() {
        return eaPurchaseContractName;
    }

    public void setEaPurchaseContractName(String eaPurchaseContractName) {
        this.eaPurchaseContractName = eaPurchaseContractName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getEaPurchaseContract() {
        return eaPurchaseContract;
    }

    public void setEaPurchaseContract(String eaPurchaseContract) {
        this.eaPurchaseContract = eaPurchaseContract;
    }


    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getEaState() {
        return eaState;
    }

    public void setEaState(String eaState) {
        this.eaState = eaState;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public String getEaDate() {
        return eaDate;
    }

    public void setEaDate(String eaDate) {
        this.eaDate = eaDate;
    }

    public Integer getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(Integer inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public Integer getUseApplyStatus() {
        return useApplyStatus;
    }

    public void setUseApplyStatus(Integer useApplyStatus) {
        this.useApplyStatus = useApplyStatus;
    }

    public Integer getEnterInspectionStatus() {
        return enterInspectionStatus;
    }

    public void setEnterInspectionStatus(Integer enterInspectionStatus) {
        this.enterInspectionStatus = enterInspectionStatus;
    }

    public String getUaId() {
        return uaId;
    }

    public void setUaId(String uaId) {
        this.uaId = uaId;
    }

    public Integer getMunNumber() {
        return munNumber;
    }

    public void setMunNumber(Integer munNumber) {
        this.munNumber = munNumber;
    }

    public Integer getEaWitness() {
        return eaWitness;
    }

    public void setEaWitness(Integer eaWitness) {
        this.eaWitness = eaWitness;
    }
}
