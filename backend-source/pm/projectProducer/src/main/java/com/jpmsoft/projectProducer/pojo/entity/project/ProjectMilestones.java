package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Project_Milestones")
public class ProjectMilestones {
    @TableId(type = IdType.INPUT)
    private String pmsId;
    private String pId;
    private String createUid;
    private String createUname;
    private Date createTime;
}
