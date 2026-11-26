package com.jpmsoft.projectProducer.pojo.entity.dh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Monthly_Inspection_Report_Qd")
public class MonthlyInspectionReportQd {
    @TableId(type = IdType.INPUT)
    private String id;
    private String mirId;
    private String code;
    private String quantity;
    private String completeQuantity;
    private String totalCompleteQuantity;
    private String fileId;
    private String name;
}
