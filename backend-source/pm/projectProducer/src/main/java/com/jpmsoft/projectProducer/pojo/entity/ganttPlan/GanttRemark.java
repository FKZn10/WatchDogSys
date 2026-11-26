package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import lombok.Data;
import java.util.Date;


// 备注表实体
@Data
public class GanttRemark {
    private String id;
    private String gmsId;
    private Date writeTime;
    private String remark;
    private String createUser;
    private String createUserName;
}