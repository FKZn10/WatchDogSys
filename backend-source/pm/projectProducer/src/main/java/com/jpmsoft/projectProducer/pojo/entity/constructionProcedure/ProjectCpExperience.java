package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 经验分享
 */
@Data
@TableName("Project_Cp_Experience")
public class ProjectCpExperience {
    @TableId(type = IdType.INPUT)
    private String cpeId;
    private String cpdId;
    private String cpeContent;
    private String cpeCreateUid;
    private String cpeCreateUname;
    private Date cpeCreateTime;
    private String teId;
}
