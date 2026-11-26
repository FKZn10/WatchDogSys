
package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案目录
 */
@Data
public class ArchiveDirectoryDTO implements Serializable {

    private static final long serialVersionUID = -6149409439996487463L;
    @ExcelValid
    @ExcelProperty(index = 0, value = "目录名称")
    private String adName;
    @ExcelProperty(index = 1, value = "目录编码")
    private String adCode;
    @ExcelProperty(index = 2, value = "归档号")
    private String adNo;
    @ExcelProperty(index = 3, value = "【保管期限】建设单位")
    private String adRpBuild;
    @ExcelProperty(index = 4, value = "【保管期限】施工单位")
    private String adRpConstruction;
    @ExcelProperty(index = 5, value = "【保管期限】设计单位")
    private String adRpDesign;
    @ExcelProperty(index = 6, value = "【保管期限】监理单位")
    private String adRpSupervisor;
    @ExcelProperty(index = 7, value = "【保管期限】城建档案馆")
    private String adRpCity;
    @ExcelProperty(index = 8, value = "版本")
    private String adVersion;
    @ExcelProperty(index = 9, value = "上级目录编码")
    private String adParentId;
    @ExcelProperty(index = 10, value = "说明")
    private String adRemark;
    @ExcelProperty(index = 11, value = "说明")
    private String adLink;
    @ExcelProperty(index = 12, value = "目录类型")
    private String adType;
    @ExcelProperty(index = 13, value = "状态")
    private Integer adState;
    @ExcelProperty(index = 14, value = "排序号")
    private Integer adOrder;

}
