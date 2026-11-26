package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import com.jpmsoft.fileUpload.easyexcel.converter.CustomStrBoolConverter;
import com.jpmsoft.fileUpload.easyexcel.converter.WanYuan2YuanConverter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * easyexcel 城建工程报告导入
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 14:03
 */
@Data
public class CjOaProjectReportDto {


    @ExcelValid
    @JsonProperty("pId")
    @ExcelProperty(index = 1)
    private String pId;

    @JsonProperty("pCompetentDepartmentName")
    @ExcelProperty(index = 2)
    private String pCompetentDepartmentName;

    @ExcelValid
    @ExcelProperty(index = 3)
    private String  prImpState;

    @ExcelValid
    @ExcelProperty(index = 4)
    private String prStartWork;

    @ExcelValid
    @ExcelProperty(index = 5)
    private Integer prInitiationPlan;
    @ExcelValid
    @ExcelProperty(index = 6)
    private String prResponsibleDep;
    @ExcelValid
    @ExcelProperty(index = 7)
    private String prResponsibleLeader;
    @ExcelValid
    @ExcelProperty(index = 8, converter = WanYuan2YuanConverter.class)
    private BigDecimal prProjectInvestment;
    @ExcelValid
    @ExcelProperty(index = 9)
    private String prInvestmentType;
    @ExcelValid
    @ExcelProperty(index = 10)
    private String prIsBudget;
    @ExcelValid
    @ExcelProperty(index = 11, converter = WanYuan2YuanConverter.class)
    private BigDecimal prBudgetInvestment;
    @ExcelValid
    @ExcelProperty(index = 12, converter = WanYuan2YuanConverter.class)
    private BigDecimal prPlannedLastInv;
    @ExcelValid
    @ExcelProperty(index = 13, converter = WanYuan2YuanConverter.class)
    private BigDecimal prDoneLastYear;
    @ExcelValid
    @ExcelProperty(index = 14)
    private BigDecimal prLastYearRate;
    @ExcelValid
    @ExcelProperty(index = 15)
    private String prIsStartWork;
    @ExcelValid
    @ExcelProperty(index = 16)
    private String prIsFinish;
    @ExcelValid
    @ExcelProperty(index = 17, converter = WanYuan2YuanConverter.class)
    private BigDecimal prPlannedInvestment;
    @ExcelValid
    @ExcelProperty(index = 18)
    private String prMainContent;
    @ExcelValid
    @ExcelProperty(index = 19, converter = WanYuan2YuanConverter.class)
    private BigDecimal prAdjustInv;
    @ExcelValid
    @ExcelProperty(index = 20)
    private String prAdjustmentType;
    @ExcelValid
    @ExcelProperty(index = 21)
    @DateTimeFormat("yyyy-MM-dd")
    private Date prAdjustmentTime;
    @ExcelValid(dirCheck = "JPMsoft_UNDER")
    @ExcelProperty(index = 22)
    private String prUrbanPlan;
    @ExcelProperty(index = 23)
    private String prInvPlan;
    @ExcelProperty(index = 24)
    private String prSocialProject;
    @ExcelProperty(index = 25)
    private String prPractical;
    @ExcelProperty(index = 26)
    private String prRefine;
    @ExcelProperty(index = 27)
    private String prKeyWork;
    @ExcelProperty(index = 28)
    private String prKeyTask;
    @ExcelProperty(index = 29)
    private String prEconomicDeve;
    @ExcelProperty(index = 30)
    private String prBrokenRoad;
    @ExcelProperty(index = 31)
    private String prLivableCity;
    @ExcelProperty(index = 32)
    private String prUrbanAction;
    @ExcelProperty(index = 33)
    private String prRefuseClass;
    @ExcelProperty(index = 34)
    private String prParking;
    @ExcelProperty(index = 35)
    private String prDistrictConst;
    @ExcelProperty(index = 36)
    private String prHandleAffairs;
    @ExcelProperty(index = 37)
    private String prOther;
    @ExcelProperty(index = 38)
    private String prRemark;
    @ExcelValid
    @ExcelProperty(index = 39)
    private String prDutyMain;
    @ExcelValid(dirCheck = "JPMsoft_PRCONSSTATE")
    @ExcelProperty(index = 40)
    private String prProUnder;
    @ExcelValid
    @ExcelProperty(index = 41)
    private String prProcedures;
    @ExcelValid
    @ExcelProperty(index = 42)
    private Integer prNumberPresent;
    @ExcelValid
    @ExcelProperty(index = 43)
    private String prImgProgress;
    @ExcelValid
    @ExcelProperty(index = 44, converter = WanYuan2YuanConverter.class)
    private BigDecimal prCompleteInv;
    @ExcelValid
    @ExcelProperty(index = 45, converter = WanYuan2YuanConverter.class)
    private BigDecimal prYearInv;
    @ExcelValid
    @ExcelProperty(index = 46)
    private BigDecimal prInvRate;
    @ExcelValid
    @ExcelProperty(index = 47, converter = WanYuan2YuanConverter.class)
    private BigDecimal prMonthInv;
    @ExcelValid
    @ExcelProperty(index = 48)
    private BigDecimal prMonthInvRate;
    @ExcelValid
    @ExcelProperty(index = 49,converter = CustomStrBoolConverter.class)
    private Boolean prWhetherLag;
    @ExcelProperty(index = 50)
    private String prReasonLag;
    @ExcelValid()
    @ExcelProperty(index = 51)
    private String prSuggest;

}
