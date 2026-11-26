package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesContext;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel.*;
import com.jpmsoft.fileUpload.service.IBillOfQuantitiesParseService;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class BillOfQuantitiesParseServiceImpl implements IBillOfQuantitiesParseService {

    @Override
    public BillOfQuantitiesContext parse(MultipartFile multipartFile) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook = null;
        assert fileName != null;
        String type = FileUtil.checkFileType(multipartFile.getInputStream());
        if (type.equalsIgnoreCase("error")) {//检测文件头失败则检测附件后缀名称
            assert fileName != null;
            if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);//Excel 2007
            } else if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(inputStream);//Excel 2003
            } else {
                throw new JPMException(FileMsg.onlyExcel);
            }
        } else {
            if (type.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);//Excel 2007
            } else if (type.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(inputStream);//Excel 2003
            } else {
                throw new JPMException(FileMsg.onlyExcel);
            }
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        BillOfQuantitiesContext billOfQuantitiesContext = new BillOfQuantitiesContext();
        List<String> parseFailedSheetNameList = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetName(i);
            Sheet sheet = workbook.getSheetAt(i);
            String sheetNameKey = sheetName.replaceFirst(i + 1 + "\\.", "");
            sheetNameKey = sheetNameKey.replaceAll(" ", "");
            String trimSheetName=sheetName.trim().replaceAll(" ","");
            if (match(Arrays.asList("表10.2.2-12招标控制价费用表", "表10.2.2-12投标报价费用表"), sheetNameKey)
            ||match(Arrays.asList("表10.2.2-12招标控制价费用表", "表10.2.2-12投标报价费用表"), trimSheetName)) {
                if (billOfQuantitiesContext.getBiddingControlPriceDTO() != null) {
                    throw new JPMException("表10.2.2-12招标控制价费用表重复");
                }
                BiddingControlPriceDTO biddingControlPriceDTO = parseBiddingControlPrice(i, sheet);
                billOfQuantitiesContext.setBiddingControlPriceDTO(biddingControlPriceDTO);
            } else if (match(Arrays.asList("表10.2.2-13专业工程招标控制价费用表", "表10.2.2-13专业工程投标报价费用表"), sheetNameKey)
            ||match(Arrays.asList("表10.2.2-13专业工程招标控制价费用表", "表10.2.2-13专业工程投标报价费用表"), trimSheetName)) {
                ProfessionalEngineeringBiddingControlPriceDTO professionalEngineeringBiddingControlPriceDTO
                        = parseProfessionalEngineeringBiddingControlPrice(i, sheet);
                String sectionName = professionalEngineeringBiddingControlPriceDTO.getSectionName();
                billOfQuantitiesContext.addProfessionalEngineeringBiddingControlPrice(sectionName, professionalEngineeringBiddingControlPriceDTO);
            } else if (match(Arrays.asList("表10.2.2-16分部分项工程清单与计价表"), sheetNameKey)||
                    match(Arrays.asList("表10.2.2-16分部分项工程清单与计价表"), trimSheetName)) {
                SubBranchSubItemEngineeringListDTO subBranchSubItemEngineeringListDTO
                        = parseSubBranchSubItemEngineeringListDTO(i, sheet);
                String engineeringName = subBranchSubItemEngineeringListDTO.getEngineeringName();
                billOfQuantitiesContext.addSubBranchSubItemEngineeringList(engineeringName, subBranchSubItemEngineeringListDTO);
            } else if (match(Arrays.asList("表10.2.2-16施工技术措施项目清单与计价表"), sheetNameKey)||
                    match(Arrays.asList("表10.2.2-16施工技术措施项目清单与计价表"), trimSheetName)) {
                ConstructionTechMeasureProjectListAndPriceTableDTO constructionTechMeasureProjectListAndPriceTableDTO
                        = parseConstructionTechMeasureProjectListAndPriceTable(i, sheet);
                String engineeringName = constructionTechMeasureProjectListAndPriceTableDTO.getEngineeringName();
                billOfQuantitiesContext.addConstructionTechMeasureProjectListAndPriceTable(engineeringName, constructionTechMeasureProjectListAndPriceTableDTO);
            } else if (match(Arrays.asList("表10.2.2-20施工组织（总价）措施项目清单与计价表"), sheetNameKey)||
                    match(Arrays.asList("表10.2.2-20施工组织（总价）措施项目清单与计价表"), trimSheetName)) {
                ConstructionOrganizationMeasureProjectListAndPriceTableDTO constructionOrganizationMeasureProjectListAndPriceTableDTO
                        = parseConstructionOrganizationMeasureProjectListAndPriceTable(i, sheet);
                String engineeringName = constructionOrganizationMeasureProjectListAndPriceTableDTO.getEngineeringName();
                billOfQuantitiesContext.addConstructionOrganizationMeasureProjectListAndPriceTable(engineeringName, constructionOrganizationMeasureProjectListAndPriceTableDTO);
            } else {
                parseFailedSheetNameList.add(sheetName);
            }
        }
        if (!parseFailedSheetNameList.isEmpty()) {
            System.out.println("解析失败表单:" + parseFailedSheetNameList);
        }
        return billOfQuantitiesContext;
    }

    /**
     * 根据表单名称匹配模板类型
     *
     * @param templateList
     * @param sheetNameKey
     * @return
     */
    private boolean match(List<String> templateList, String sheetNameKey) {
        if (CollUtil.isEmpty(templateList) || StrUtil.isBlank(sheetNameKey)) {
            return false;
        }
        for (String template : templateList) {
            if (sheetNameKey.contains(template)) {
                return true;
            }
        }
        return false;
    }

    private ConstructionOrganizationMeasureProjectListAndPriceTableDTO parseConstructionOrganizationMeasureProjectListAndPriceTable(int sheetIndex, Sheet sheet) {
        ConstructionOrganizationMeasureProjectListAndPriceTableDTO constructionOrganizationMeasureProjectListAndPriceTableDTO = new ConstructionOrganizationMeasureProjectListAndPriceTableDTO();
        constructionOrganizationMeasureProjectListAndPriceTableDTO.setSheetIndex(sheetIndex);
        constructionOrganizationMeasureProjectListAndPriceTableDTO.setSheetName(sheet.getSheetName());

        String engineeringName = sheet.getRow(2).getCell(0).getStringCellValue();
        constructionOrganizationMeasureProjectListAndPriceTableDTO.setEngineeringName(engineeringName);

        int lastRowNum = sheet.getLastRowNum();
        List<ConstructionOrganizationMeasureProjectListAndPriceTableEntryDTO> entryList = new ArrayList<>(lastRowNum);

        for (int i = 4; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell indexCell = row.getCell(0);
            DataFormatter dataFormatter = new DataFormatter();
            String indexStr = dataFormatter.formatCellValue(indexCell);
            if (!isIndexValue(indexStr)) {
                continue;
            }
            ConstructionOrganizationMeasureProjectListAndPriceTableEntryDTO entry = new ConstructionOrganizationMeasureProjectListAndPriceTableEntryDTO();
            entry.setIndex(indexStr);

            String projectName = row.getCell(1).getStringCellValue();
            entry.setProjectName(projectName);

            Cell feeRateCell = row.getCell(3);
            entry.setFeeRate(dataFormatter.formatCellValue(feeRateCell));

            Cell amountCell = row.getCell(4);
            entry.setAmount(dataFormatter.formatCellValue(amountCell));

            Cell remarkCell = row.getCell(5);
            entry.setRemark(remarkCell.getStringCellValue());

            entryList.add(entry);
        }

        constructionOrganizationMeasureProjectListAndPriceTableDTO.setEntryList(entryList);

        return constructionOrganizationMeasureProjectListAndPriceTableDTO;
    }

    private ConstructionTechMeasureProjectListAndPriceTableDTO parseConstructionTechMeasureProjectListAndPriceTable(int sheetIndex, Sheet sheet) {
        ConstructionTechMeasureProjectListAndPriceTableDTO constructionTechMeasureProjectListAndPriceTableDTO = new ConstructionTechMeasureProjectListAndPriceTableDTO();
        constructionTechMeasureProjectListAndPriceTableDTO.setSheetIndex(sheetIndex);
        constructionTechMeasureProjectListAndPriceTableDTO.setSheetName(sheet.getSheetName());

        String engineeringName = sheet.getRow(2).getCell(0).getStringCellValue();
        engineeringName = engineeringName.replace("专业工程名称：", "");
        constructionTechMeasureProjectListAndPriceTableDTO.setEngineeringName(engineeringName);

        int lastRowNum = sheet.getLastRowNum();
        List<ListAndPriceTableEntryDTO> entryList = new ArrayList<>(lastRowNum);
        for (int i = 6; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell indexCell = row.getCell(0);
            DataFormatter dataFormatter = new DataFormatter();
            String indexStr = dataFormatter.formatCellValue(indexCell);
            if (!isIndexValue(indexStr)) {
                continue;
            }
            String projectName = row.getCell(2).getStringCellValue();
            if (StrUtil.isBlank(projectName)) {
                continue;
            }

            ListAndPriceTableEntryDTO entry = new ListAndPriceTableEntryDTO();
            entry.setIndex(indexStr);

            String projectCode = row.getCell(1).getStringCellValue();
            entry.setProjectCode(projectCode);

            entry.setProjectName(projectName);

            String projectFeature = row.getCell(3).getStringCellValue();
            entry.setProjectFeature(projectFeature);

            String measureUnit = row.getCell(4).getStringCellValue();
            entry.setMeasureUnit(measureUnit);

            Cell engineeringQuantitiesCell = row.getCell(5);
            String engineeringQuantities = dataFormatter.formatCellValue(engineeringQuantitiesCell);
            entry.setEngineeringQuantity(engineeringQuantities);

            Cell comprehensiveUnitPriceCell = row.getCell(6);
            entry.setComprehensiveUnitPrice(dataFormatter.formatCellValue(comprehensiveUnitPriceCell));

            Cell combinePriceCell = row.getCell(7);
            entry.setCombinePrice(dataFormatter.formatCellValue(combinePriceCell));

            Cell remarkCell = row.getCell(11);
            String remark = dataFormatter.formatCellValue(remarkCell);
            entry.setRemark(remark);

            entryList.add(entry);
        }
        constructionTechMeasureProjectListAndPriceTableDTO.setEntryList(entryList);

        return constructionTechMeasureProjectListAndPriceTableDTO;
    }

    private SubBranchSubItemEngineeringListDTO parseSubBranchSubItemEngineeringListDTO(int sheetIndex, Sheet sheet) {
        SubBranchSubItemEngineeringListDTO subBranchSubItemEngineeringListDTO = new SubBranchSubItemEngineeringListDTO();
        subBranchSubItemEngineeringListDTO.setSheetIndex(sheetIndex);
        subBranchSubItemEngineeringListDTO.setSheetName(sheet.getSheetName());

        String engineeringName = sheet.getRow(2).getCell(0).getStringCellValue();
        engineeringName = engineeringName.replace("专业工程名称：", "");
        subBranchSubItemEngineeringListDTO.setEngineeringName(engineeringName);

        int lastRowNum = sheet.getLastRowNum();
        List<ListAndPriceTableEntryDTO> entryList = new ArrayList<>(lastRowNum);
        for (int i = 6; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell indexCell = row.getCell(0);
            DataFormatter dataFormatter = new DataFormatter();
            String indexStr = dataFormatter.formatCellValue(indexCell);
            if (!isIndexValue(indexStr)) {
                continue;
            }
            String projectName = row.getCell(2).getStringCellValue();
            if (StrUtil.isBlank(projectName)) {
                continue;
            }

            ListAndPriceTableEntryDTO entry = new ListAndPriceTableEntryDTO();
            entry.setIndex(indexStr);

            String projectCode = row.getCell(1).getStringCellValue();
            entry.setProjectCode(projectCode);

            entry.setProjectName(projectName);

            String projectFeature = row.getCell(3).getStringCellValue();
            entry.setProjectFeature(projectFeature);

            String measureUnit = row.getCell(4).getStringCellValue();
            entry.setMeasureUnit(measureUnit);

            Cell engineeringQuantitiesCell = row.getCell(5);
            String engineeringQuantities = dataFormatter.formatCellValue(engineeringQuantitiesCell);
            entry.setEngineeringQuantity(engineeringQuantities);

            Cell comprehensiveUnitPriceCell = row.getCell(6);
            entry.setComprehensiveUnitPrice(dataFormatter.formatCellValue(comprehensiveUnitPriceCell));

            Cell combinePriceCell = row.getCell(7);
            entry.setCombinePrice(dataFormatter.formatCellValue(combinePriceCell));

            Cell remarkCell = row.getCell(11);
            String remark = dataFormatter.formatCellValue(remarkCell);
            entry.setRemark(remark);

            entryList.add(entry);
        }
        subBranchSubItemEngineeringListDTO.setEntryList(entryList);
        return subBranchSubItemEngineeringListDTO;
    }

    private BiddingControlPriceDTO parseBiddingControlPrice(int sheetIndex, Sheet sheet) {
        BiddingControlPriceDTO biddingControlPriceDTO = new BiddingControlPriceDTO();
        biddingControlPriceDTO.setSheetIndex(sheetIndex);
        biddingControlPriceDTO.setSheetName(sheet.getSheetName());
        String sectionName = sheet.getRow(2).getCell(0).getStringCellValue();
        biddingControlPriceDTO.setSectionName(sectionName.replace("工程名称：", ""));
        int lastRowNum = sheet.getLastRowNum();
        int startIndex = 5;
        List<BiddingControlPriceEntryDTO> entryList = new ArrayList<>(lastRowNum);
        for (int i = startIndex; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell indexCell = row.getCell(0);
            DataFormatter dataFormatter = new DataFormatter();
            String indexStr = dataFormatter.formatCellValue(indexCell);
            if (StrUtil.isNotBlank(indexStr) && indexStr.startsWith("合")) {
                Cell cell = row.getCell(2);
                biddingControlPriceDTO.setTotalPrice(dataFormatter.formatCellValue(cell));
            }
            if (!isIndexValue(indexStr)) {
                continue;
            }
            BiddingControlPriceEntryDTO biddingControlPriceEntryDTO = new BiddingControlPriceEntryDTO();
            biddingControlPriceEntryDTO.setIndex(indexStr);
            // 工程名称
            String engineeringName = row.getCell(1).getStringCellValue();
            biddingControlPriceEntryDTO.setSectionName(engineeringName);

            Cell amountCell = row.getCell(2);
            biddingControlPriceEntryDTO.setMoneyAmount(dataFormatter.formatCellValue(amountCell));

            entryList.add(biddingControlPriceEntryDTO);
        }
        biddingControlPriceDTO.setEntryList(entryList);
        return biddingControlPriceDTO;
    }

    private boolean isIndexValue(String str) {
        if (StrUtil.isBlank(str)) {
            return true;
        }
        if (NumberUtil.isNumber(str.replaceAll("\\.", ""))) {
            return true;
        }
        return false;
    }

    private ProfessionalEngineeringBiddingControlPriceDTO parseProfessionalEngineeringBiddingControlPrice(int sheetIndex, Sheet sheet) {
        ProfessionalEngineeringBiddingControlPriceDTO professionalEngineeringBiddingControlPriceDTO = new ProfessionalEngineeringBiddingControlPriceDTO();
        professionalEngineeringBiddingControlPriceDTO.setSheetIndex(sheetIndex);
        professionalEngineeringBiddingControlPriceDTO.setSheetName(sheet.getSheetName());
        // 工程名称
        String engineeringName = sheet.getRow(2).getCell(0).getStringCellValue();
        engineeringName = engineeringName.replace("单位工程名称：", "");
        professionalEngineeringBiddingControlPriceDTO.setSectionName(engineeringName);

        DataFormatter dataFormatter = new DataFormatter();

        Row titleRow = sheet.getRow(3);
        int lastCellNum = titleRow.getLastCellNum();
        Map<Integer, String> columnIndexToNameMap = new HashMap<>();
        for (int i = 0; i <= lastCellNum; i++) {
            columnIndexToNameMap.put(i, dataFormatter.formatCellValue(titleRow.getCell(i)));
        }

        int lastRowNum = sheet.getLastRowNum();
        List<ProfessionalEngineeringBiddingControlPriceEntryDTO> entryList = new ArrayList<>(lastRowNum);

        String[] columnNames = {"序号", "费用名称", "计算公式", "金额（元）", "费率(%)", "备注"};

        for (int i = 4; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell indexCell = row.getCell(0);
            String indexStr = dataFormatter.formatCellValue(indexCell);
            if (!isIndexValue(indexStr)) {
                continue;
            }
            ProfessionalEngineeringBiddingControlPriceEntryDTO entry = new ProfessionalEngineeringBiddingControlPriceEntryDTO();
            entry.setIndex(indexStr);

            String feeName = row.getCell(1).getStringCellValue();
            entry.setFeeName(feeName);

            String calculatorFormula = row.getCell(2).getStringCellValue();
            entry.setFormula(calculatorFormula);

            for (int j = 3; j <= row.getLastCellNum(); j++) {
                String columnTitleName = columnIndexToNameMap.get(j);
                if (columnNames[3].equals(columnTitleName)) {
                    Cell amountCell = row.getCell(j);
                    String amount = dataFormatter.formatCellValue(amountCell);
                    entry.setAmount(amount);
                } else if (columnNames[4].equals(columnTitleName)) {
                    Cell feeRateCell = row.getCell(j);
                    String feeRate = dataFormatter.formatCellValue(feeRateCell);
                    entry.setFeeRate(feeRate);
                } else if (columnNames[5].equals(columnTitleName)) {
                    Cell remarkCell = row.getCell(j);
                    entry.setRemark(dataFormatter.formatCellValue(remarkCell));
                }
            }


            entryList.add(entry);
        }
        professionalEngineeringBiddingControlPriceDTO.setEntryList(entryList);
        return professionalEngineeringBiddingControlPriceDTO;
    }

}
