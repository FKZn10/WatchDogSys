package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 进度计划执行反馈导入导出
 *
 * @Author: 李文涛 aaron
 * @date: 2024/4/12 8:59
 */
@Data
public class GanttTaskFeedbackDTO implements Serializable {

    private static final long serialVersionUID = -8863315597232060941L;
    @ExcelValid
    @ExcelProperty(index = 0, value = "任务编码")
    private String taskId;
    @ExcelProperty(index = 1, value = "计划名称")
    private String planName;
    @JsonProperty("pName")
    @ExcelProperty(index = 2, value = "所属项目")
    private String pName;
    @ExcelProperty(index = 3, value = "上级节点名称")
    private String taskParentText;
    @ExcelProperty(index = 4, value = "节点名称")
    private String taskText;
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 5, value = "计划开始时间")
    private Date taskStartDate;
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 6, value = "计划完成时间")
    private Date taskEndDate;
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 7, value = "实际开始时间")
    private Date taskBeginTime;
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 8, value = "实际完成时间")
    private Date taskFinishTime;
    @ExcelProperty(index = 9, value = "优先级")
    private String taskPriority;
    @ExcelProperty(index = 10, value = "要求成果")
    private String taskDoc;
    @ExcelProperty(index = 11, value = "工期(天)")
    private Float taskDuration;
    @ExcelProperty(index = 12, value = "完成进度(%)")
    private Float taskProgress;
    @ExcelProperty(index = 13, value = "本次完成进度(%)")
    private Float gtpRate;
    @ExcelProperty(index = 14, value = "填报日期")
    private Date gtpDate;
    @ExcelProperty(index = 15, value = "形象进度填报")
    private String gtpImageProgress;
    @ExcelProperty(index = 16, value = "责任人")
    private String taskUname;
    @ExcelProperty(index = 17, value = "现场照片")
    private String gtpScenePhotos;
}
