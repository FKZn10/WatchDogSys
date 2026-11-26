package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 建设程序模板项目类型配置
 */
@Data
@TableName("Project_Cp_Config_Relation")
public class ProjectCpConfigRelation {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String cpltId;
    private String pType;
}