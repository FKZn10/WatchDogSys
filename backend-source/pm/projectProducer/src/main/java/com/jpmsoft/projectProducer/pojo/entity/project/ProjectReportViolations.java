package com.jpmsoft.projectProducer.pojo.entity.project;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Project_Report_Violations")
public class ProjectReportViolations {

    @TableId(type = IdType.ASSIGN_ID)
    private String prvId;
    private String prvCreateUid;
    private Date prvCreateTime;
    private String prvCreateName;
    private Integer prvState;
    private Date prvStateTime;

    private String pId;
    private String prvCompany;
    private String prvQuestion;
    private String prvRemark;
    private String prvPhoto;
    private String prvIdCardImg;
    private String prvPhone;
}