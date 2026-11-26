package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * TODO 专家库 Excel导入DTO
 */
@Data
public class SecExpertExcelDTO {
    @ExcelProperty(index = 0, value = "序号")
    private String num;
    @ExcelProperty(index = 1, value = "姓名")
    private String exName;
    @ExcelProperty(index = 2, value = "通讯方式")
    private String exHomePhone;
    // 技术类专家申报专业1   JPMsoft_SBZYYI
    @ExcelProperty(index = 3, value = "专家申报专业1")
    private String exDeclareMajorOne;
    // 技术类专家申报专业2   JPMsoft_SBZYER
    @ExcelProperty(index = 4, value = "专家申报专业2")
    private String exDeclareMajorTwo;
    @ExcelProperty(index = 5, value = "集团系统任职年限")
    private Integer exSysWorkYear;
    // JPMsoft_ZYJSZC
    @ExcelProperty(index = 6, value = "专业技术职称")
    private String exTitle;
    // 专家类型 JPMsoft_ZJLB
    @ExcelProperty(index = 7, value = "专家类型")
    private String exCategory;
}
