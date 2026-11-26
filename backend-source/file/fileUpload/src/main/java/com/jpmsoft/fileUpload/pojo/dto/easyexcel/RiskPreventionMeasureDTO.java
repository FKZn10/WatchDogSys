package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险点防控措施
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 8:45
 */
@Data
public class RiskPreventionMeasureDTO implements Serializable {
    @ExcelValid
    @ExcelProperty(index = 0, value = "风险点")
    private String rpmPoints;
    @ExcelProperty(index = 1, value = "责任主体")
    private String rpmDep;
    @ExcelProperty(index = 2, value = "风险内容")
    private String rpmContent;
    @ExcelProperty(index = 3, value = "风险类别")
    private String rpmType;
    @ExcelProperty(index = 4, value = "风险等级")
    private String rpmLevel;
    @ExcelProperty(index = 5, value = "防控措施")
    private String rpmMeasure;
    @ExcelProperty(index = 6, value = "所涉对象")
    private String rpmInvolved;
    @ExcelProperty(index = 7, value = "图标")
    private String rpmIcon;
    @ExcelProperty(index = 8, value = "图标颜色")
    private String rpmIconColor;
    /**
     * 临时编码存储的是原ID值 与上级编码形成上下级关系
     */
    @ExcelProperty(index = 9, value = "编码")
    private String temporaryCode;
    @ExcelProperty(index = 10, value = "上级编码")
    private String rpmParentId;
    @ExcelProperty(index = 11, value = "状态")
    private String rpmStatus;
    @ExcelProperty(index = 12, value = "排序号")
    private Integer rpmSort;
}
