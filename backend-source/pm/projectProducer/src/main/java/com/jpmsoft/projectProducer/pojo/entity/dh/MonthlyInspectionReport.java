package com.jpmsoft.projectProducer.pojo.entity.dh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Monthly_Inspection_Report")
public class MonthlyInspectionReport {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private String secId;
    private String conId;
    private String secEngineeringContent;
    private String mainProjectProgress;
    private Date fillDate;
    private String sectionManager;
}
