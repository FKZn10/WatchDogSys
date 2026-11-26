package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author jjz
 */
@Data
@TableName("task_distribution")
public class TaskDistribution {

    @TableId
    private String tdId;

    // 项目id
    private String pId;

    // 任务名称
    private String taskName;

    // 任务编码
    private String taskCode;

    // 对应图纸表的id 弃用
    private String diId;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    // 对应新图纸表id
    private String dmId;

    // 图纸操作授权用户集合，多个用户用英文逗号分隔。
    // 原逻辑是“用户有检查点才等于能看到图纸”；现在允许先授权用户看到/操作图纸，
    // 后续再由用户动态新增检查点，所以授权关系放在任务主表上，旧检查点逻辑继续兼容。
    private String taskUserIds;

    // 图纸操作授权用户名称集合，多个用户名称由前端拼接后直接传入。
    // 这里只负责原样保存并返回，方便列表/详情直接展示，不参与权限判断。
    private String taskUserNames;

    private String teId;

}
