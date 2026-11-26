package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import com.jpmsoft.fileUpload.easyexcel.converter.CustomStrBoolConverter;
import com.jpmsoft.fileUpload.easyexcel.converter.WanYuan2YuanConverter;
import lombok.Data;

import java.math.BigDecimal;

/**
 * easyexcel工程月报导入
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 14:03
 */
@Data
public class OaProjectReportMonDto {

    @ExcelValid
    @JsonProperty("pId")
    @ExcelProperty(index = 1)
    private String pId;

    @JsonProperty("pCompetentDepartmentName")
    @ExcelProperty(index = 2)
    private String pCompetentDepartmentName;

    @ExcelValid
    @ExcelProperty(index = 3,converter = WanYuan2YuanConverter.class)
    private BigDecimal prDoneLastYear;

    @ExcelValid
    @ExcelProperty(index = 4,converter = WanYuan2YuanConverter.class)
    private BigDecimal prPlannedInvestment;

    @ExcelValid
    @ExcelProperty(index = 5)
    private String prTImgProgress;

    @ExcelValid
    @ExcelProperty(index = 6)
    private String prProgressQuarterOne;

    @ExcelValid
    @ExcelProperty(index = 7,converter = WanYuan2YuanConverter.class)
    private BigDecimal prInvQuarterOne;

    @ExcelValid
    @ExcelProperty(index = 8)
    private String prProgressQuarterTwo;

    @ExcelValid
    @ExcelProperty(index = 9,converter = WanYuan2YuanConverter.class)
    private BigDecimal prInvQuarterTwo;

    @ExcelValid
    @ExcelProperty(index = 10)
    private String prProgressQuarterThree;

    @ExcelValid
    @ExcelProperty(index = 11,converter = WanYuan2YuanConverter.class)
    private BigDecimal prInvQuarterThree;

    @ExcelValid
    @ExcelProperty(index = 12)
    private String prProgressQuarterFour;

    @ExcelValid
    @ExcelProperty(index = 13,converter = WanYuan2YuanConverter.class)
    private BigDecimal prInvQuarterFour;

    @ExcelValid
    @ExcelProperty(index = 14)
    private String prProUnder;

    @ExcelValid
    @ExcelProperty(index = 15)
    private String prProcedures;

    @ExcelValid
    @ExcelProperty(index = 16)
    private String prImgProgress;

    @ExcelValid
    @ExcelProperty(index = 17,converter = WanYuan2YuanConverter.class)
    private BigDecimal prCompleteInv;

    @ExcelValid
    @ExcelProperty(index = 18,converter = WanYuan2YuanConverter.class)
    private BigDecimal prYearInv;

    @ExcelValid
    @ExcelProperty(index = 19)
    private BigDecimal prInvRate;

    @ExcelValid
    @ExcelProperty(index = 20,converter = WanYuan2YuanConverter.class)
    private BigDecimal prMonthInv;

    @ExcelValid
    @ExcelProperty(index = 21)
    private BigDecimal prMonthInvRate;

    @ExcelProperty(index = 22,converter = CustomStrBoolConverter.class)
    private Boolean prWhetherLag;

    @ExcelProperty(index = 23)
    private String prReasonLag;

    @ExcelValid
    @ExcelProperty(index = 24)
    private String prSuggest;
}
