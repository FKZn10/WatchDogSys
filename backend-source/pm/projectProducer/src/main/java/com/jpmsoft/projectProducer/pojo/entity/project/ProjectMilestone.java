package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Project_Milestone")
public class ProjectMilestone {
    @TableId(type = IdType.INPUT)
    private String pmId;
    private String pmsId;
    private String title;
    private String description;
    private Integer sort;
    private Integer status;

    private String content;
    private Date startTime;
    private Date endTime;
    private String unitId;
    private String userId;
    private String userName;
    private String result;
    private String createUid;
    private String createUname;
    private Date createTime;
}
