package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 基建投资月报清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:02
 */
@Data
public class InvInfrastructureMonthDtlDto  {

    @ExcelValid
    @ExcelProperty(index = 0)
    private String imdSchoolName;
    @ExcelValid
    @JsonProperty("pId")
    @ExcelProperty(index = 1)
    private String pId;
    @ExcelValid
    @ExcelProperty(index = 3)
    private BigDecimal imdSurplusFunds;
    @ExcelValid
    @ExcelProperty(index = 4)
    private BigDecimal imdCollectTotal;
    @ExcelValid
    @ExcelProperty(index = 5)
    private BigDecimal imdCollectCountry;
    @ExcelValid
    @ExcelProperty(index = 6)
    private BigDecimal imdCollectSchool;
    @ExcelValid
    @ExcelProperty(index = 7)
    private BigDecimal imdCollectGovernment;
    @ExcelValid
    @ExcelProperty(index = 8)
    private BigDecimal imdCollectDonate;
    @ExcelValid
    @ExcelProperty(index = 9)
    private BigDecimal imdCollectOther;
    @ExcelValid
    @ExcelProperty(index = 10)
    private BigDecimal imdOtherFunds;
    @ExcelValid
    @ExcelProperty(index = 11)
    private BigDecimal imdPayTotal;
    @ExcelValid
    @ExcelProperty(index = 12)
    private BigDecimal imdPayCountry;
    @ExcelValid
    @ExcelProperty(index = 13)
    private BigDecimal imdPaySchool;
    @ExcelValid
    @ExcelProperty(index = 14)
    private BigDecimal imdPayGovernment;
    @ExcelValid
    @ExcelProperty(index = 15)
    private BigDecimal imdPayDonate;
    @ExcelValid
    @ExcelProperty(index = 16)
    private BigDecimal imdPayOther;
    @ExcelValid
    @ExcelProperty(index = 17)
    private BigDecimal imdReturnFunds;
    @ExcelProperty(index = 18)
    private BigDecimal imdYearBalance;
    @ExcelValid
    @ExcelProperty(index = 19)
    private BigDecimal imdPlanFunds;
    @ExcelProperty(index = 20)
    private String imdRemark;
    @ExcelIgnore
    private Integer imdSort;
}
