package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 待办首次阅读信息
 */
@Data
@TableName("sys_task_first_read")
public class SysTaskFirstRead {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 待办id
     */
    private String taskId;

    /**
     * 项目id
     */
    private String pId;

    /**
     * 合同id
     */
    private String conId;

    /**
     * 表单id
     */
    private String entityId;

    /**
     * 流程id
     */
    private String flowId;

    /**
     * 流程节点id
     */
    private String actId;

    /**
     * 流程节点编码
     */
    private String actCode;

    /**
     * 阅读时间
     */
    private Date readTime;

    /**
     * 阅读人
     */
    private String readUser;

    /**
     * 流程编码
     */
    private String tCode;
}