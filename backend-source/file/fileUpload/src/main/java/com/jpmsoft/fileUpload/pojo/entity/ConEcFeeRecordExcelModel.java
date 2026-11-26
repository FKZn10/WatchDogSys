package com.jpmsoft.fileUpload.pojo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Excel 导入模型
 * @author jjz
 */
@Data
public class ConEcFeeRecordExcelModel {

    // 序号
    @ExcelProperty(index = 0)
    private Integer mearOrder;

    // 子目或单项名称
    @ExcelProperty(index = 1)
    private String mearBoqdName;

    // 单位
    @ExcelProperty(index = 2)
    private String mearBoqdUnit;

    // 施工单位本期实际工程量（BigDecimal）
    @ExcelProperty(index = 3)
    private BigDecimal mearDeclareAmount;

    // 施工单位累计工程量（BigDecimal）
    @ExcelProperty(index = 4)
    private BigDecimal mearUpperAmount;

    // 施工单位单价（BigDecimal）
    @ExcelProperty(index = 5)
    private BigDecimal mearConPrice;

    // 施工单位本期总价（BigDecimal）
    @ExcelProperty(index = 6)
    private BigDecimal mearConTotal;
}
