package com.jpmsoft.fileUpload.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class conTractDTO {


    @ExcelIgnore
    @JsonProperty("conId")
    private String conId;
    @JsonProperty("pName")
    @ExcelProperty("项目名称")
    @ColumnWidth(80)
    private String pName;

    @JsonProperty("conName")
    @ExcelProperty("合同名称")
    @ColumnWidth(80)
    private String conName;
    @JsonProperty("conCode")
    @ExcelProperty("合同编码")
    @ColumnWidth(30)
    private String conCode;

    @JsonProperty("conType")
    @ExcelProperty("合同类型")
    @ColumnWidth(30)
    private String conType;

    @JsonProperty("conPartyAName")
    @ExcelProperty("甲方单位")
    @ColumnWidth(50)
    private String conPartyAName;

    @JsonProperty("conPartyBName")
    @ExcelProperty("乙方单位")
    @ColumnWidth(50)
    private String conPartyBName;

    @JsonProperty("conMoneyTotal")
    @ExcelProperty("合同金额(元)")
    @ColumnWidth(50)
    @ContentStyle(
            horizontalAlignment = HorizontalAlignmentEnum.RIGHT,  // 设置水平对齐方式为靠右
            verticalAlignment = VerticalAlignmentEnum.CENTER    // 设置垂直对齐方式为居中
    )
    private BigDecimal conMoneyTotal;

    @ExcelIgnore
    private Date conDate;

    @JsonProperty("conDateTime")
    @ExcelProperty("签订时间")
    @ColumnWidth(40)
    private String conDateTime;

    @JsonProperty("conCreateTime")
    @ExcelProperty("创建时间")
    @ColumnWidth(40)
    private  Date conCreateTime;

    @JsonProperty("conCreateUserName")
    @ExcelProperty("创建人")
    @ColumnWidth(40)
    private  String conCreateUserName;

    @JsonProperty("state")
    @ExcelProperty("流程状态")
    @ColumnWidth(40)
    private String state;
    @ExcelIgnore
    private Integer conApprovalState;


}
