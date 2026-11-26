package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工作办理记录
 */
@Data
@TableName("Project_Cp_Work_Record")
public class ProjectCpWorkRecord {
    @TableId(type = IdType.INPUT)
    private String cpwrId;
    private String cpdId;
    private String cpwrRemark;
    private String cpwrCreateUid;
    private String cpwrCreateUname;
    private Date cpwrCreateTime;
    private String teId;
    private Boolean cpwrHomePush;
    private Date cpwrFillingTime;
}
