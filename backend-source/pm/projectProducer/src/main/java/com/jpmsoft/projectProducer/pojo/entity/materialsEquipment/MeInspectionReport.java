package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 检测报告
 */
@Data
@TableName("Me_Inspection_Report")
public class MeInspectionReport {

    @TableId(type = IdType.INPUT)
    private String irId;
    private String wiId;
    //检测指引ID
    private String dgId;
    private String pId;
    private String conId;
    private String irName;
    private Integer irResult;
    private Date irDate;
    private String irRemark;
    private String irCreateUid;
    private String irCreateUname;
    private Date irCreateTime;
    private Integer irState;
    private Date irStateTime;
    private String teId;
}
