package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文档模板
 */
@Data
public class SysFileTemplateDto implements Serializable {

    private static final long serialVersionUID = -8594308653547202842L;
    @ExcelIgnore
    private String tId;
    @ExcelValid
    @ExcelProperty(index = 0, value = "模板名称")
    private String tName;
    @ExcelValid
    @ExcelProperty(index = 1, value = "模板编码")
    private String tCode;
    @ExcelValid
    @ExcelProperty(index = 2, value = "模板状态")
    private String tStats;
    @ExcelProperty(index = 3, value = "模板类型")
    private String tType;
    @ExcelProperty(index = 4, value = "审批")
    private String tNeedflow;
    @ExcelProperty(index = 5, value = "暂存")
    private String tAllowdraft;
    @ExcelProperty(index = 6, value = "文档处理页面")
    private String tFlowpage;
    @ExcelProperty(index = 7, value = "文档修改页面")
    private String tInfopage;
    @ExcelProperty(index = 8, value = "文档详细页面")
    private String tUpdatepage;
    @ExcelProperty(index = 9, value = "文档打印页面")
    private String tPrintpage;
    @ExcelProperty(index = 10, value = "是否系统模板")
    private String tSystem;
    @ExcelProperty(index = 11, value = "是否通用模版")
    private String tGeneral;
    @ExcelProperty(index = 12, value = "文档word页面")
    private String tWordpage;
    @ExcelProperty(index = 13, value = "文档Excel页面")
    private String tExcelpage;
    @ExcelProperty(index = 14, value = "文档报表页面")
    private String tReportpage;
    @ExcelProperty(index = 15, value = "协同处理页面")
    private String tCoordinationpage;
    @ExcelProperty(index = 16, value = "模板内容")
    private String tContent;
    @ExcelProperty(index = 17, value = "模板描述")
    private String tDescription;
    @ExcelProperty(index = 18, value = "使用帮助")
    private String tHelper;
    @ExcelProperty(index = 19, value = "数据表")
    private String tTable;
    @ExcelProperty(index = 20, value = "上级模板")
    private String tParentid;
    @ExcelProperty(index = 21, value = "所属客户")
    private String tSolution;
    @ExcelProperty(index = 22, value = "客户端编码")
    private String tClient;
    @ExcelProperty(index = 23, value = "数据表主键")
    private String tPrimaryKey;
    @ExcelProperty(index = 24, value = "通用附件提醒消息")
    private String tFileTips;
    @ExcelProperty(index = 25, value = "数据表流程状态")
    //流程字段
    private String tStateKey;
    @ExcelProperty(index = 26, value = "数据表流程状态变更时间")
    private String tStateTimeKey;
    @ExcelProperty(index = 27, value = "列表查询接口地址")
    //表单设计字段
    private String tListRequestUrl;
    @ExcelProperty(index = 28, value = "列表查询请求方式")
    private String tListRequestType;
    @ExcelProperty(index = 29, value = "列表查询请求参数")
    private String tListRequestParameter;
    @ExcelProperty(index = 30, value = "详情查询接口地址")
    private String tDetailsRequestUrl;
    @ExcelProperty(index = 31, value = "详情查询接口请求方式")
    private String tDetailsRequestType;
    @ExcelProperty(index = 32, value = "详情查询过接口请求参数")
    private String tDetailsRequestParameter;
    @ExcelProperty(index = 33, value = "编辑接口地址")
    private String tCreateRequestUrl;
    @ExcelProperty(index = 34, value = "编辑接口请求方式")
    private String tCreateRequestType;
    @ExcelProperty(index = 35, value = "编辑接口请求参数")
    private String tCreateRequestParameter;
    @ExcelProperty(index = 36, value = "列表配置内容")
    private String tColumnContent;
    @ExcelProperty(index = 37, value = "分页大小")
    private String tPageSize;
    @ExcelProperty(index = 38, value = "列表插槽模板")
    private String tSlotTemplate;
    @ExcelValid
    @ExcelProperty(index = 39, value = "排序号")
    private Integer tSort;
    @ColumnWidth(17)
    @ExcelProperty(index = 40, value = "创建时间")
    private Date tCreatetime;
    @ExcelProperty(index = 41, value = "创建人")
    private String tCreateuser;
    @ExcelProperty(index = 42, value = "创建人姓名")
    private String tCreateusername;
    @ExcelProperty(index = 43, value = "流程任务名称")
    private String tTaskName;
    @ExcelProperty(index = 44, value = "APP流程页")
    private String tAppFlowpage;
    @ExcelProperty(index = 45, value = "APP详情页")
    private String tAppInfopage;
    @ExcelProperty(index = 46, value = "APP修改页")
    private String tAppUpdatepage;
    @ExcelProperty(index = 47, value = "附件上传时是否必须选择归档目录")
    private Boolean tIsArchivists;
    @ExcelProperty(index = 48, value = "列表设置")
    private String tTableSet;
    @ExcelProperty(index = 49, value = "是否只允许草稿")
    private String tIsDraft;

}
