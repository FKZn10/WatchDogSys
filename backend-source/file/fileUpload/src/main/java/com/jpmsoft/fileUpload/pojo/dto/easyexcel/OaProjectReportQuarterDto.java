package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import com.jpmsoft.fileUpload.easyexcel.converter.WanYuan2YuanConverter;
import lombok.Data;

import java.math.BigDecimal;

/**
 * easyexcel 季报导入
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 14:03
 */
@Data
public class OaProjectReportQuarterDto {

    @ExcelValid
    @JsonProperty("pName")
    @ExcelProperty(index = 0)
    private String pName;

    @ExcelValid
    @JsonProperty("pCode")
    @ExcelProperty(index = 1)
    private String pCode;

    @ExcelValid
    @ExcelProperty(index = 2)
    private String prQuarterlyImageSchedule;

    @ExcelValid
    @ExcelProperty(index = 3, converter = WanYuan2YuanConverter.class)
    private BigDecimal prQuarterlyCompleteInvestment;

    @ExcelValid
    @ExcelProperty(index = 4)
    private BigDecimal prQuarterlyFinishRate;

    @ExcelIgnore
    @JsonProperty("pId")
    private String pId;
}
