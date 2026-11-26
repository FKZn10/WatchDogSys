package com.jpmsoft.projectProducer.utils;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.util.List;

public class PoiUtil {

    /**
     * 设置下拉框数据验证
     *
     * @param sheet         工作表
     * @param columnIndex   列索引
     * @param startRow      开始行
     * @param endRow        结束行
     * @param dropDownSheet 下拉框数据Sheet
     */
    public void setDropDownValidation(Sheet sheet, int columnIndex, int startRow, int endRow, Sheet dropDownSheet) {
        int lastRowNum = dropDownSheet.getLastRowNum();
        CellRangeAddressList addressList = new CellRangeAddressList(startRow, endRow, columnIndex, 0);
        // 创建下拉框数据验证对象，并设置数据来源为Sheet2的第一列数据
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        String formula = dropDownSheet.getSheetName() + "!$A$1:$A$" + (lastRowNum + 1);
        DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(formula);
        DataValidation validation = validationHelper.createValidation(constraint, addressList);
        // 将下拉框数据验证对象应用到Sheet1的指定列
        sheet.addValidationData(validation);
    }

    /**
     * 设置下拉框数据验证
     *
     * @param sheet         工作表
     * @param valuelist       下拉框数据
     * @param firstRow      开始行
     * @param lastRow       结束行
     * @param firstCol      开始列
     * @param lastCol       结束列
     */
    public static void setDropDownValidation(Sheet sheet, List<String> valuelist , int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 创建下拉框数据验证对象，并设置数据来源为Sheet2的第一列数据
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        //String values = sheet.getSheetName() + "!$A$1:$A$" + (1 + 1);
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(valuelist.toArray(new String[0]));
        DataValidation validation = validationHelper.createValidation(constraint, addressList);
        //处理Excel兼容性问题
        if(validation instanceof XSSFDataValidation) {
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
        }else {
            validation.setSuppressDropDownArrow(false);
        }
        // 将下拉框数据验证对象应用到Sheet1的指定列
        sheet.addValidationData(validation);
    }


}
