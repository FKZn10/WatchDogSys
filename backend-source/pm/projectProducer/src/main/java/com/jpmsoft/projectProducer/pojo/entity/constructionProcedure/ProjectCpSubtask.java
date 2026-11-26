package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 子任务
 */
@Data
@TableName("Project_Cp_Subtask")
public class ProjectCpSubtask {
    @TableId(type = IdType.INPUT)
    public String pcsId;
    public String cpdId;
    public String pcsTaskName;
    public String pcsHandler;
    public Date pcsPlanStartTime;
    public Date pcsPlanEndTime;
    public Date pcsActualStartTime;
    public Date pcsActualOkTime;
    public String pcsTaskContent;
    public String pcsOkStatus;
    public String pcsRemark;
    public String pcsProve;
    public String pcsCreateUname;
    public String pcsCreateUid;
    public Date pcsCreateTime;
    private String teId;

}
