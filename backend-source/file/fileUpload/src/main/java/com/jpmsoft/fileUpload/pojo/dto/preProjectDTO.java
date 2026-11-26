package com.jpmsoft.fileUpload.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class preProjectDTO {


    @ExcelIgnore
    private String pId;
    @JsonProperty("pName")
    @ExcelProperty("项目名称")
    @ColumnWidth(40)
    private String pName;

    @JsonProperty("pOverview")
    @ColumnWidth(100)
    @ExcelProperty("项目概况")
    @ContentStyle(
            horizontalAlignment = HorizontalAlignmentEnum.CENTER,
            verticalAlignment = VerticalAlignmentEnum.CENTER,
            wrapped = BooleanEnum.TRUE)
    private String pOverview;

    @JsonProperty("pOverview")
    @ColumnWidth(20)
    @ExcelProperty("项目阶段")
    @ExcelIgnore
    private String pProjectStage;

    @JsonProperty("pProjectStatus")
    @ColumnWidth(20)
    @ExcelProperty("工程进展状态")
    private String pProjectStatus;

    @JsonProperty("pIsProjectApproval")
    @ExcelIgnore
    private Boolean pIsProjectApproval;

    @ColumnWidth(20)
    @JsonProperty("pIsProjectApprovalxs")
    @ExcelProperty("是否取得立项批复")
    private String pIsProjectApprovalxs;

    @ExcelIgnore
    private Date pConstructionStart;

    @ExcelIgnore
    private Date pConstructionEnd;

    @JsonProperty("pConstructionTime")
    @ColumnWidth(20)
    @ExcelProperty("建设起止年月")
    private String pConstructionTime;


    @JsonProperty("pType")
    @ExcelProperty("项目类别")
    @ColumnWidth(20)
    private String pType;

    @JsonProperty("pCode")
    @ColumnWidth(20)
    @ExcelProperty("项目编码")
    private String pCode;


    @JsonProperty("pUseUnit")
    @ColumnWidth(40)
    @ExcelProperty("建设单位")
    private String pUseUnit;

    @ExcelIgnore
    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;


    @JsonProperty("pCompetentDepartmentName")
    @ExcelProperty("全过程咨询/项目管理单位")
    @ColumnWidth(40)
    private String pCompetentDepartmentName;

    @JsonProperty("pInvTotal")
    @ExcelProperty("项目总投资（立项投资）")
    @ColumnWidth(40)
    private BigDecimal pInvTotal;

    @JsonProperty("pApprovalUnit")
    @ExcelProperty("立项审批单位")
    @ColumnWidth(40)
    private String pApprovalUnit;

    @JsonProperty("pSourcesFunds")
    @ExcelProperty("资金来源")
    @ColumnWidth(40)
    private String pSourcesFunds;

    @ExcelIgnore
    @JsonProperty("state")
    private Integer state;


    @JsonProperty("pCreateUserName")
    @ExcelProperty("创建人")
    @ColumnWidth(20)
    private String pCreateUserName;

    @JsonProperty("pCreateTime")
    @ExcelProperty("创建时间")
    @ColumnWidth(40)
    private Date pCreateTime;

    @JsonProperty("stateName")
    @ExcelProperty("流程状态")
    @ColumnWidth(20)
    private String stateName;


}
