package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OutlineDatasourceDTO {
    @ExcelProperty(index = 0, value = "id")
    private String id;
    @ExcelProperty(index = 1, value = "名称")
    private String odName;
    @ExcelProperty(index = 2, value = "类型")
    private String odType;
    @ExcelProperty(index = 3, value = "sql内容")
    private String odSource;
    @ExcelProperty(index = 4, value = "状态")
    private String odStatus;
    @ExcelProperty(index = 5, value = "备注")
    private String odRemark;
    @ExcelProperty(index = 6, value = "api数据源地址")
    private String odAddress;
    @ExcelProperty(index = 7, value = "请求方式")
    private String odMethod;
    @ExcelProperty(index = 8, value = "请求体")
    private String odBody;
    @ExcelProperty(index = 9, value = "数据源别名")
    private String odAlias;
    @ExcelProperty(index = 10, value = "数据源类型")
    private String odDataType;
    @ExcelProperty(index = 11, value = "创建人uid")
    private String createUid;
    @ExcelProperty(index = 12, value = "创建人姓名")
    private String createUname;
    @ExcelProperty(index = 13, value = "创建时间")
    private Date createTime;
    @ExcelProperty(index = 14, value = "更新时间")
    private Date updateTime;
    @ExcelProperty(index = 15, value = "文档模板")
    private String tCodes;
}
