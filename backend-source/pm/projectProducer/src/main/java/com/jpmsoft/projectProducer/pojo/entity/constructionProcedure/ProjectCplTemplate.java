package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 建设程序项目模板
 */
@Data
@TableName("Project_Cpl_Template")
public class ProjectCplTemplate {

    @TableId(type = IdType.INPUT)
    private String cpltId;
    private String cpltName;
    private String cpltPtype;
    private Date cpltCreateTime;
    private String cpltCreateUid;
    private String cpltCreateUname;
    private String cpltRemark;
    private Date cpltEditTime;
    private String cpltState;
    private String teId;

}
