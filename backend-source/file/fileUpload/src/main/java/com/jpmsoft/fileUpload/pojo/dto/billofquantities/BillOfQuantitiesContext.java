package com.jpmsoft.fileUpload.pojo.dto.billofquantities;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BillOfQuantitiesContext {

    // 招标控制价费用
    private BiddingControlPriceDTO biddingControlPriceDTO;

    // 工程量清单
    private List<BillOfQuantities> billOfQuantitiesList = new ArrayList<>();

    private Map<String, BillOfQuantities> sectionNameToBillOfQuantitiesMap = new HashMap<>();

    public String getMeasureFeeRate(){
        return billOfQuantitiesList.get(0).getMeasureFeeRate();
    }

    public String getRegulatoryFeeRate(){
        return billOfQuantitiesList.get(0).getRegulatoryFeeRate();
    }

    public String getTaxFeeRate(){
        return billOfQuantitiesList.get(0).getTaxFeeRate();
    }

    public boolean addProfessionalEngineeringBiddingControlPrice(String sectionName, ProfessionalEngineeringBiddingControlPriceDTO professionalEngineeringBiddingControlPriceDTO) {
        if (StrUtil.isBlank(sectionName)) {
            return false;
        }
        sectionName = getShortKey(sectionName);
        BillOfQuantities billOfQuantities = getOrInstantiateBillOfQuantities(sectionName);
        billOfQuantities.setProfessionalEngineeringBiddingControlPriceDTO(professionalEngineeringBiddingControlPriceDTO);
        return true;
    }

    public boolean addSubBranchSubItemEngineeringList(String sectionName, SubBranchSubItemEngineeringListDTO subBranchSubItemEngineeringListDTO) {
        if (StrUtil.isBlank(sectionName)) {
            return false;
        }
        sectionName = getShortKey(sectionName);
        BillOfQuantities billOfQuantities = getOrInstantiateBillOfQuantities(sectionName);
        billOfQuantities.setSubBranchSubItemEngineeringListDTO(subBranchSubItemEngineeringListDTO);
        return true;
    }

    public boolean addConstructionTechMeasureProjectListAndPriceTable(String sectionName, ConstructionTechMeasureProjectListAndPriceTableDTO constructionTechMeasureProjectListAndPriceTableDTO) {
        if (StrUtil.isBlank(sectionName)) {
            return false;
        }
        sectionName = getShortKey(sectionName);
        BillOfQuantities billOfQuantities = getOrInstantiateBillOfQuantities(sectionName);
        billOfQuantities.setConstructionTechMeasureProjectListAndPriceTableDTO(constructionTechMeasureProjectListAndPriceTableDTO);
        return true;
    }

    public boolean addConstructionOrganizationMeasureProjectListAndPriceTable(String sectionName, ConstructionOrganizationMeasureProjectListAndPriceTableDTO constructionOrganizationMeasureProjectListAndPriceTableDTO) {
        if (StrUtil.isBlank(sectionName)) {
            return false;
        }
        sectionName = getShortKey(sectionName);
        BillOfQuantities billOfQuantities = getOrInstantiateBillOfQuantities(sectionName);
        billOfQuantities.setConstructionOrganizationMeasureProjectListAndPriceTableDTO(constructionOrganizationMeasureProjectListAndPriceTableDTO);
        return true;
    }

    private BillOfQuantities getOrInstantiateBillOfQuantities(String sectionName) {
        BillOfQuantities billOfQuantities = sectionNameToBillOfQuantitiesMap.get(sectionName);
        if (billOfQuantities == null) {
            billOfQuantities = new BillOfQuantities();
            billOfQuantities.setSectionName(sectionName);
            billOfQuantitiesList.add(billOfQuantities);
            sectionNameToBillOfQuantitiesMap.put(sectionName, billOfQuantities);
        }
        return billOfQuantities;
    }

    private String getShortKey(String sectionName) {
        for (String key : sectionNameToBillOfQuantitiesMap.keySet()) {
            if (sectionName.endsWith(key)) {
                sectionName = key;
            }
        }
        for (String key : sectionNameToBillOfQuantitiesMap.keySet()) {
            if (key.endsWith(sectionName)) {
                sectionName = key;
            }
        }
        return sectionName;
    }

    public String getTotalPriceBySectionName(String sectionName) {
        String shortKey = getShortKey(sectionName);
        List<BiddingControlPriceEntryDTO> entryList = biddingControlPriceDTO.getEntryList();
        if (CollUtil.isEmpty(entryList)){
            return null;
        }
        for (BiddingControlPriceEntryDTO entry : entryList) {
            if (shortKey.equals(getShortKey(entry.getSectionName()))){
                return entry.getMoneyAmount();
            }
        }
        return null;
    }
}
