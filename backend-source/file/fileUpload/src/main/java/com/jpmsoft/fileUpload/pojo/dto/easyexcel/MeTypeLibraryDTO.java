package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;

/**
 * 材料设备品种库 导入导出
 */
@Data
public class MeTypeLibraryDTO implements Serializable {

    private static final long serialVersionUID = -1649385354991633721L;

    @ExcelValid
    @ExcelProperty(index = 0, value = "类型名称")
    private String metName;
    @ExcelProperty(index = 1, value = "编码")
    private String metCode;
    @ExcelProperty(index = 2, value = "管理类型字典")
    private String metManageType;
    @ExcelProperty(index = 3, value = "单位")
    private String metUnit;
    @ExcelProperty(index = 4, value = "上级编码")
    private String metParentId;
    @ExcelProperty(index = 5, value = "数据类型")
    private String metType;
    @ExcelProperty(index = 6, value = "标签")
    private String metTag;
    @ExcelProperty(index = 7, value = "状态")
    private Integer metState;
    @ExcelProperty(index = 8, value = "排序号")
    private Integer metSort;
}
