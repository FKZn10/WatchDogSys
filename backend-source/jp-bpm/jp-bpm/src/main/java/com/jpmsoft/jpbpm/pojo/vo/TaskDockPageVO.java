package com.jpmsoft.jpbpm.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 对接查询用户待办信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/12/21 14:11
 */
@Data
public class TaskDockPageVO implements Serializable {

    /**
     * 待办ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 项目名称
     */
    @JsonProperty("pName")
    private String pName;
    /**
     * 合同名称
     */
    private String conName;
    /**
     * 标段名称
     */
    private String secName;
    /**
     * 待办发送人名称
     */
    private String sendUserName;
    /**
     * 任务处理人名称
     */
    private String taskUserName;
    /**
     * 任务发送时间
     */
    private String taskSendTime;
    /**
     * 查看状态
     */
    private String taskRead;
    /**
     * 阅读时间
     */
    private String taskReadTime;
    /**
     * 流程名称
     */
    private String flowName;
    /**
     * 流程创建人名称
     */
    private String flowCreateUname;
    /**
     * 流程发起时间
     */
    private String flowCreateTime;
    /**
     * 流程状态
     */
    private String flowState;
    /**
     * 任务剩余时长
     */
    private BigDecimal remainder;
    /**
     * web跳转地址
     */
    private String taskWebUrl;
}
