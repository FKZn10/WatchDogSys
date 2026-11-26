package com.jpmsoft.projectProducer.pojo.vo.zjt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ZjtExportDataVO {

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pLongitude")
    private Float pLongitude;
    @JsonProperty("pLatitude")
    private Float pLatitude;
    @JsonProperty("pType")
    private String pType;
    @JsonProperty("pProfessionalType")
    private String pProfessionalType;
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    @JsonProperty("pConstructionArea")
    private Float pConstructionArea;
    @JsonProperty("pBuildingUnit")
    private String pBuildingUnit;
    @JsonProperty("pTotalDuration")
    private Integer pTotalDuration;
    @JsonProperty("pScaleDescribe")
    private String pScaleDescribe;
    @JsonProperty("pCalculateRoughlyAmount")
    private BigDecimal pCalculateRoughlyAmount;
    @JsonProperty("pEstimateAmount")
    private BigDecimal pEstimateAmount;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;

    @JsonProperty("pPlanStartDate")
    private Date pPlanStartDate;
    @JsonProperty("pActualStartTime")
    private Date pActualStartTime;
    @JsonProperty("pPlanCompleteDate")
    private Date pPlanCompleteDate;

    @JsonProperty("pBim")
    private String pBim;
    @JsonProperty("pSmartSite")
    private String pSmartSite;
    @JsonProperty("pManagementSystem")
    private String pManagementSystem;
    @JsonProperty("pNewTechnology")
    private String pNewTechnology;
    @JsonProperty("pNewMaterial")
    private String pNewMaterial;
    @JsonProperty("pNewCraft")
    private String pNewCraft;
    @JsonProperty("pSaveEnergy")
    private String pSaveEnergy;
    @JsonProperty("pSaveLand")
    private String pSaveLand;
    @JsonProperty("pSaveWater")
    private String pSaveWater;
    @JsonProperty("pSaveMaterial")
    private String pSaveMaterial;
    @JsonProperty("pIsPrefabricated")
    private String pIsPrefabricated;
    @JsonProperty("pDigitalConstruction")
    private String pDigitalConstruction;
    @JsonProperty("pGreenConstruction")
    private String pGreenConstruction;
    @JsonProperty("pCentralizedConstruction")
    private String pCentralizedConstruction;
    @JsonProperty("pContractingMode")
    private String pContractingMode;
    @JsonProperty("pProcessConsulting")
    private String pProcessConsulting;
    @JsonProperty("pEngineerResponsible")
    private String pEngineerResponsible;

}
