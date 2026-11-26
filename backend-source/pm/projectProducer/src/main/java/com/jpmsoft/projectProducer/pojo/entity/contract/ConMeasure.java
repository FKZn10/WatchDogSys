package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计量报审表
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/08 10:07
 * @TableName Con_Measure
 */
@TableName(value = "Con_Measure")
@Data
public class ConMeasure {
    /**
     *
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     *
     */
    @TableField(value = "Con_Id")
    private String conId;

    @TableField(value = "P_Id")
    private String pId;

    /**
     *
     */
    @TableField(value = "Mea_Supervisor_Id")
    private String meaSupervisorId;

    /**
     *
     */
    @TableField(value = "Mea_Start_Time")
    private Date meaStartTime;

    /**
     *
     */
    @TableField(value = "Mea_End_Time")
    private Date meaEndTime;

    /**
     *
     */
    @TableField(value = "Mea_Type")
    private String meaType;

    /**
     *
     */
    @TableField(value = "Mea_Code")
    private String meaCode;

    /**
     *
     */
    @TableField(value = "Mea_Title")
    private String meaTitle;

    /**
     *
     */
    @TableField(value = "Mea_Book")
    private String meaBook;

    /**
     *
     */
    @TableField(value = "Mea_Book_Page")
    private Integer meaBookPage;

    /**
     *
     */
    @TableField(value = "Mea_Change_Notice")
    private String meaChangeNotice;

    /**
     *
     */
    @TableField(value = "Mea_Contractor_Pm")
    private String meaContractorPm;

    /**
     *
     */
    @TableField(value = "Mea_Contractor_Id")
    private String meaContractorId;

    /**
     *
     */
    @TableField(value = "Create_Uid")
    private String createUid;

    /**
     *
     */
    @TableField(value = "Create_Uname")
    private String createUname;

    /**
     *
     */
    @TableField(value = "Create_Time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "State")
    private Integer state;

    /**
     *
     */
    @TableField(value = "State_Time")
    private Date stateTime;

    /**
     *
     */
    @TableField(value = "Mea_Remark")
    private String meaRemark;

    @TableField(value = "Mea_Sub_Item_Amount")
    private BigDecimal meaSubItemAmount;

    @TableField(value = "Mea_Step_Amount")
    private BigDecimal meaStepAmount;

    @TableField(value = "Mea_UnitPrice_Amount")
    private BigDecimal meaUnitPriceAmount;

    @TableField(value = "Mea_TotalPrice_Amount")
    private BigDecimal meaTotalPriceAmount;

    @TableField(value = "Mea_OtherItem_Amount")
    private BigDecimal meaOtherItemAmount;

    @TableField(value = "Mea_Fee")
    private BigDecimal meaFee;

    @TableField(value = "Mea_Tax")
    private BigDecimal meaTax;

    @TableField(value = "Mea_Labour")
    private BigDecimal meaLabour;

    @TableField(value = "Mea_Other_Amount")
    private BigDecimal meaOtherAmount;

    @TableField(value = "Mea_Completed")
    private BigDecimal meaCompleted;
    @TableField(value = "Mea_Apply_Amt")
    private BigDecimal meaApplyAmt;
    @TableField(value = "Mea_Period_Amt")
    private BigDecimal meaPeriodAmt;
    @TableField(value = "Mea_Currently_Amt")
    private BigDecimal meaCurrentlyAmt;

    @TableField(value = "Mea_Tax_Proportion")
    private Float meaTaxProportion;

    @TableField(value = "Mea_Labour_Proportion")
    private Float meaLabourProportion;

    @TableField(value = "Mea_Pay_Proportion")
    private Float meaPayProportion;
    private String teId;

    /**
     * 变更立项id
     */
    @TableField(value = "con_Ec_Id")
    private String conEcId;

    /**
     * 数据分离 01 以前 02 当前
     */
    @TableField(value = "Mear_Data_Separate")
    private String mearDataSeparate;
}