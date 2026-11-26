package com.jpmsoft.fileUpload.pojo.dto.billofquantities;

import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.ConstructionOrganizationMeasureProjectListAndPriceTableDTO;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.ConstructionTechMeasureProjectListAndPriceTableDTO;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.ProfessionalEngineeringBiddingControlPriceDTO;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.SubBranchSubItemEngineeringListDTO;
import lombok.Data;

/**
 * 工程量清单
 */
@Data
public class BillOfQuantities {

    // 工程名称
    private String sectionName;

    // 专业工程招标控制价
    private ProfessionalEngineeringBiddingControlPriceDTO professionalEngineeringBiddingControlPriceDTO;

    // 分部分项工程清单与计价表
    private SubBranchSubItemEngineeringListDTO subBranchSubItemEngineeringListDTO;

    // 施工技术措施项目清单与计价表
    private ConstructionTechMeasureProjectListAndPriceTableDTO constructionTechMeasureProjectListAndPriceTableDTO;

    // 施工组织措施项目清单与计价表
    private ConstructionOrganizationMeasureProjectListAndPriceTableDTO constructionOrganizationMeasureProjectListAndPriceTableDTO;

    public String getMeasureFeeRate() {
        if (constructionOrganizationMeasureProjectListAndPriceTableDTO == null) {
            return null;
        }
        return constructionOrganizationMeasureProjectListAndPriceTableDTO.getMeasureFeeRate();
    }

    public String getRegulatoryFeeRate() {
        if (professionalEngineeringBiddingControlPriceDTO == null) {
            return "0";
        }
        return professionalEngineeringBiddingControlPriceDTO.getRegulatoryFeeRate();
    }

    public String getTaxFeeRate() {
        if (professionalEngineeringBiddingControlPriceDTO == null) {
            return null;
        }
        return professionalEngineeringBiddingControlPriceDTO.getTaxRate();
    }

    public String getRegulatoryFee() {
        return professionalEngineeringBiddingControlPriceDTO.getRegulatoryFee();
    }

    public String getTax(){
        return professionalEngineeringBiddingControlPriceDTO.getTax();
    }
}
