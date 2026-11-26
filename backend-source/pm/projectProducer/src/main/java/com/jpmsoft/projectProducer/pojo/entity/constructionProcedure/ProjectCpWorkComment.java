package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 11:54
 */
@Data
@TableName("Project_Cp_Work_Comment")
public class ProjectCpWorkComment {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cpwrId;
    private String cpwcContent;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String teId;
}
