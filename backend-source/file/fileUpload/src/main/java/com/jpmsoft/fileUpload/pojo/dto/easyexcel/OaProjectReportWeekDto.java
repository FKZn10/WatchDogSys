package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import com.jpmsoft.fileUpload.easyexcel.converter.CustomStrBoolConverter;
import com.jpmsoft.fileUpload.easyexcel.converter.WanYuan2YuanConverter;
import lombok.Data;

import java.math.BigDecimal;

/**
 * easyexcel工程周报导入
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 14:03
 */
@Data
public class OaProjectReportWeekDto {

    @ExcelValid
    @JsonProperty("pId")
    @ExcelProperty(index = 1)
    private String pId;

    @ExcelProperty(index = 2)
    private String prConstruction;

    @ExcelProperty(index = 3)
    private String prSafeProduction;

    @ExcelProperty(index = 4)
    private String prInspection;

    @ExcelProperty(index = 5)
    private String prProUnder;

    @ExcelProperty(index = 6)
    private String prProcedures;

    @ExcelProperty(index = 7)
    private String prImgProgress;

    @ExcelProperty(index = 8,converter = WanYuan2YuanConverter.class)
    private BigDecimal prCompleteInv;

    @ExcelProperty(index = 9,converter = WanYuan2YuanConverter.class)
    private BigDecimal prYearInv;

    @ExcelProperty(index = 10)
    private BigDecimal prInvRate;

    @ExcelProperty(index = 11,converter = WanYuan2YuanConverter.class)
    private BigDecimal prMonthInv;

    @ExcelProperty(index = 12)
    private BigDecimal prMonthInvRate;

    @ExcelProperty(index = 13, converter = CustomStrBoolConverter.class)
    private Boolean prWhetherLag;

    @ExcelProperty(index = 14)
    private String prReasonLag;

    @ExcelProperty(index = 15)
    private String prSuggest;

}
