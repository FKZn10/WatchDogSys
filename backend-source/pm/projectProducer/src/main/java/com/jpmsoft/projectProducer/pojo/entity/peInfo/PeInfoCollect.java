package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 考核评分汇总表
 */
@Data
@TableName("Pe_Info_Collect")
public class PeInfoCollect {
    @TableId(type = IdType.INPUT)
    private String pecId;
    private Date pecDate;
    private String pecUid;
    private String pecUname;
    private String pecConstructionUnitId;
    private String pecUnitId;
    private Date pecFlowDate;
    private Integer pecFlowState;
    private String pecRemark;
    private Integer pecYear;
    private Integer pecQuarter;
    private String pecType;
    private String pecDepartment;
    private String teId;

}
