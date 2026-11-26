package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("project_preset_year")
public class ProjectPresetYear {

    @TableId(type = IdType.INPUT)
    private String id;

    @TableField("Create_Uid")
    private String createUid;

    @TableField("Create_Uname")
    private String createUname;

    @TableField("Create_Time")
    private Date createTime;

    @TableField("State")
    private Integer state;

    @TableField("State_Time")
    private Date stateTime;

    @TableField("P_Id")
    private String pId;

    @TableField("Py_Eclare_Year")
    private Integer pyEclareYear;

    @TableField("Py_Investment_Amount")
    private BigDecimal pyInvestmentAmount;
}
