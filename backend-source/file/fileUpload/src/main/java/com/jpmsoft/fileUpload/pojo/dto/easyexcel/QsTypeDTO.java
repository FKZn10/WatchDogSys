package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 质量安全类别 导入导出
 */
@Data
public class QsTypeDTO implements Serializable {

    private static final long serialVersionUID = 5369287412026013496L;

    @ExcelValid
    @ExcelProperty(index = 0, value = "类别名称")
    private String qstName;
    @ExcelProperty(index = 1, value = "类别编码")
    private String qstCode;
    @ExcelProperty(index = 2, value = "上级类别编码")
    private String qstParentId;
    @ExcelProperty(index = 3, value = "类别归类")
    private String qstCategory;
    @ExcelProperty(index = 4, value = "评价项目字典编码")
    private String qstItem;
    @ExcelProperty(index = 5, value = "备注")
    private String qstRemark;
    @ExcelProperty(index = 6, value = "状态")
    private Integer qstState;
    @ExcelProperty(index = 7, value = "排序号")
    private Integer qstSort;
}
