package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 项目建设程序
 */
@Data
@TableName("Project_Cp_Change")
public class ProjectCpChange {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cpId;
    @JsonProperty("pId")
    private String pId;
    private Date cpCreateTime;
    private String cpCreateUid;
    private String cpCreateUname;
    private String cpRemark;
    private String cpltId;
    private Integer state;
    private Date stateTime;
    private String teId;

}
