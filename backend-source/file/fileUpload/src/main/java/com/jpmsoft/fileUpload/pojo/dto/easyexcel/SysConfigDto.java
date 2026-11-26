package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础配置导入导出
 */
@Data
public class SysConfigDto implements Serializable {

    private static final long serialVersionUID = -6442129901160639949L;
    @ExcelValid
    @ExcelProperty(index = 0, value = "所属类别")
    private String conCategory;
    @ExcelValid
    @ExcelProperty(index = 1, value = "是否上传文件")
    private Integer conType;
    @ExcelValid
    @ExcelProperty(index = 2, value = "名称")
    public String conName;
    @ExcelValid
    @ExcelProperty(index = 3, value = "代码")
    public String conCode;
    @ExcelValid(olnySpace = false)
    @ExcelProperty(index = 4, value = "值")
    public String conValue;
    @ExcelProperty(index = 5, value = "描述")
    public String conDescription;
    @ExcelProperty(index = 6, value = "创建人ID")
    public String conCreateuId;
    @ExcelProperty(index = 7, value = "创建人名称")
    public String conCreateuName;
    @ExcelProperty(index = 8, value = "创建时间")
    @ColumnWidth(17)//宽度太窄显示成#####
    public Date conCreatetime;
    @ExcelProperty(index = 9, value = "启用状态")
    private Integer conStatus;
}

