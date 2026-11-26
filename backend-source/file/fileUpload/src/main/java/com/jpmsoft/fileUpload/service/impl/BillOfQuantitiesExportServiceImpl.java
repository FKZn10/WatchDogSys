package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantities;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesContext;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesExportDTO;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.ListAndPriceTableEntryDTO;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.SubBranchSubItemEngineeringListDTO;
import com.jpmsoft.fileUpload.service.IBillOfQuantitiesExportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillOfQuantitiesExportServiceImpl implements IBillOfQuantitiesExportService {

    @Override
    public List<BillOfQuantitiesExportDTO> export(BillOfQuantitiesContext billOfQuantitiesContext) {
        if (billOfQuantitiesContext == null) {
            throw new JPMException("解析失败");
        }
        if (billOfQuantitiesContext.getBiddingControlPriceDTO() == null) {
            throw new JPMException("解析失败，未解析到【投标报价费用表】或【招标控制价费用表】");
        }
        List<BillOfQuantitiesExportDTO> list = new ArrayList<>();
        List<BillOfQuantities> billOfQuantitiesList = billOfQuantitiesContext.getBillOfQuantitiesList();
        list.add(BillOfQuantitiesExportDTO.builder()
                .category("02")
                .index("一")
                .projectCode("")
                .projectName(billOfQuantitiesContext.getBiddingControlPriceDTO().getSectionName())
                .engineeringQuantity("1")
                .totalPrice(billOfQuantitiesContext.getBiddingControlPriceDTO().getTotalPrice())
//                .measureFeeRate(billOfQuantitiesContext.getMeasureFeeRate())
//                .regulatoryFeeRate(billOfQuantitiesContext.getRegulatoryFeeRate())
//                .taxFeeRate(billOfQuantitiesContext.getTaxFeeRate())
                .build());
        for (int i = 0; i < billOfQuantitiesList.size(); i++) {
            BillOfQuantities billOfQuantities = billOfQuantitiesList.get(i);
            BillOfQuantitiesExportDTO billOfQuantitiesExportDTO = BillOfQuantitiesExportDTO.builder()
                    .category("01")
                    .index(StrUtil.format("{}", i + 1))
                    .projectCode("")
                    .projectName(billOfQuantities.getSectionName())
                    .engineeringQuantity("1")
                    .totalPrice(billOfQuantitiesContext.getTotalPriceBySectionName(billOfQuantities.getSectionName()))
                    .measureFeeRate(billOfQuantities.getMeasureFeeRate())
                    .regulatoryFeeRate(billOfQuantities.getRegulatoryFeeRate())
                    .taxFeeRate(billOfQuantities.getTaxFeeRate())
                    .otherProjectFeeRate("0")
//                    .regulatoryFee(billOfQuantities.getRegulatoryFee())
//                    .taxFee(billOfQuantities.getTax())
                    .build();
            list.add(billOfQuantitiesExportDTO);
            SubBranchSubItemEngineeringListDTO subBranchSubItemEngineeringListDTO = billOfQuantities.getSubBranchSubItemEngineeringListDTO();
            int index = 0;
            int smallIndex = 1;
            List<ListAndPriceTableEntryDTO> entryList =null;
            if (subBranchSubItemEngineeringListDTO!=null) {
                entryList=subBranchSubItemEngineeringListDTO.getEntryList();
                for (ListAndPriceTableEntryDTO entry : entryList) {
                    String projectCode = entry.getProjectCode();
                    String indexStr = null;
                    if (StrUtil.isBlank(projectCode)) {
                        index++;
                        smallIndex = 1;
                        indexStr = StrUtil.format("{}.{}", i + 1, index);
                    } else {
                        indexStr = StrUtil.format("{}.{}.{}", i + 1, index, smallIndex++);
                    }
                    list.add(BillOfQuantitiesExportDTO.builder()
                            .category(StrUtil.isBlank(projectCode) ? "03" : "06")
                            .index(indexStr)
                            .projectCode(projectCode)
                            .projectName(entry.getProjectName())
                            .engineeringQuantity(entry.getEngineeringQuantity())
                            .comprehensiveUnitPrice(entry.getComprehensiveUnitPrice())
                            .totalPrice(entry.getCombinePrice())
                            .measureFeeRate(billOfQuantities.getMeasureFeeRate())
                            .regulatoryFeeRate(billOfQuantities.getRegulatoryFeeRate())
                            .taxFeeRate(billOfQuantities.getTaxFeeRate())
                            .projectFeature(entry.getProjectFeature())
                            .measureUnit(entry.getMeasureUnit())
                            .otherProjectFeeRate("0")
                            .remark(entry.getRemark())
//                        .taxFee(billOfQuantities.getTax())
//                        .regulatoryFee(billOfQuantities.getRegulatoryFee())
                            .build());
                }
            }
            if (billOfQuantities.getConstructionTechMeasureProjectListAndPriceTableDTO()!=null) {
                entryList = billOfQuantities.getConstructionTechMeasureProjectListAndPriceTableDTO().getEntryList();
                for (ListAndPriceTableEntryDTO entry : entryList) {
                    String projectCode = entry.getProjectCode();
                    String indexStr = null;
                    if (StrUtil.isBlank(projectCode)) {
                        index++;
                        smallIndex = 1;
                        indexStr = StrUtil.format("{}.{}", i + 1, index);
                    } else {
                        indexStr = StrUtil.format("{}.{}.{}", i + 1, index, smallIndex++);
                    }
                    list.add(BillOfQuantitiesExportDTO.builder()
                            .category(StrUtil.isBlank(projectCode) ? "03" : "06")
                            .index(indexStr)
                            .projectCode(projectCode)
                            .projectName(entry.getProjectName())
                            .engineeringQuantity(entry.getEngineeringQuantity())
                            .comprehensiveUnitPrice(entry.getComprehensiveUnitPrice())
                            .totalPrice(entry.getCombinePrice())
                            .measureFeeRate(billOfQuantities.getMeasureFeeRate())
                            .regulatoryFeeRate(billOfQuantities.getRegulatoryFeeRate())
                            .taxFeeRate(billOfQuantities.getTaxFeeRate())
                            .projectFeature(entry.getProjectFeature())
                            .measureUnit(entry.getMeasureUnit())
                            .otherProjectFeeRate("0")
                            .remark(entry.getRemark())
//                        .taxFee(billOfQuantities.getTax())
//                        .regulatoryFee(billOfQuantities.getRegulatoryFee())
                            .build());
                }
            }
        }
        return list;
    }

}
