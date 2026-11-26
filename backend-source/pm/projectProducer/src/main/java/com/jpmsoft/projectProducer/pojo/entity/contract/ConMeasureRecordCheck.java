package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计量单审核记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/08 10:07
 * @TableName Con_Measure_Record_Check
 */
@TableName(value = "Con_Measure_Record_Check")
@Data
public class ConMeasureRecordCheck {
    /**
     *
     */
    @TableId(value = "Mearc_Id", type = IdType.INPUT)
    private String mearcId;

    /**
     *
     */
    @TableField(value = "Mear_Id")
    private String mearId;

    /**
     *
     */
    @TableField(value = "Mearc_Amount")
    private Float mearcAmount;

    /**
     *
     */
    @TableField(value = "Mearc_File")
    private String mearcFile;

    /**
     *
     */
    @TableField(value = "Mearc_Time")
    private Date mearcTime;

    /**
     *
     */
    @TableField(value = "Mearc_Uid")
    private String mearcUid;

    /**
     *
     */
    @TableField(value = "Mearc_Uname")
    private String mearcUname;

    /**
     *
     */
    @TableField(value = "Mearc_Cid")
    private String mearcCid;

    /**
     *
     */
    @TableField(value = "Mearc_Node_Name")
    private String mearcNodeName;

    /**
     *
     */
    @TableField(value = "Mearc_Explain")
    private String mearcExplain;


    @TableField(value = "Mearc_Examine_Total")
    private BigDecimal mearcExamineTotal;
    private String teId;
}