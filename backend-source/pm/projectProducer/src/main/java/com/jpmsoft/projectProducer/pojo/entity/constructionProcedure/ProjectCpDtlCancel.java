package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 取消项目建设程序详情
 */
@Data
@TableName("Project_Cp_Dtl_Cancel")
public class ProjectCpDtlCancel {

    @TableId(type = IdType.INPUT)
    private String cdcId;
    private String pId;
    private String cpdId;
    private String cdcRemark;
    private String cdcCreateUid;
    private String cdcCreateUname;
    private Date cdcCreateTime;
    private Integer state;
    private Date stateTime;
    private String teId;
}
