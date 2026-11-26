
package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目建设程序库 导入导出
 */
@Data
public class ProjectCpLibraryDTO implements Serializable {


    private static final long serialVersionUID = -4206945079692912032L;
    @ExcelValid
    @ExcelProperty(index = 0, value = "建设程序名称")
    private String cplName;
    @ExcelProperty(index = 1, value = "建设程序编码")
    private String cplCode;
    @ExcelProperty(index = 2, value = "上级建设程序编码")
    private String cplParentId;
    @ExcelProperty(index = 3, value = "类型")
    private String cplType;
    @ExcelProperty(index = 4, value = "办理天数")
    private Integer cplDays;
    @ExcelProperty(index = 5, value = "前置建设程序编码")
    private String cplProId;
    @ExcelProperty(index = 6, value = "行政部门")
    private String cplAdministration;
    @ExcelProperty(index = 7, value = "官网链接（办理地址）")
    private String cplAdministrationUrl;
    @ExcelProperty(index = 8, value = "表单")
    private String cplHtml;
    @ExcelProperty(index = 9, value = "官方（申请材料）帮助地址")
    private String cplAdministrationUrlH;
    @ExcelProperty(index = 10, value = "文档模板编码")
    private String tCode;
    @ExcelProperty(index = 11, value = "文件上传说明")
    private String cplFileExpiain;
    @ExcelProperty(index = 12, value = "成果类型")
    private String cplAchievementType;
    @ExcelProperty(index = 13, value = "说明")
    private String cplRemark;
    @ExcelProperty(index = 14, value = "状态")
    private String cplState;
    @ExcelProperty(index = 15, value = "排序号")
    private Integer cplSort;

}
