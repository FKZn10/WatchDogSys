package com.jpmsoft.projectProducer.pojo.entity.conBoq;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单变更主表
 */
@Data
@TableName("Con_Boq_Change")
public class ConBoqChange {

    /**
     * AI识别后台任务ID。
     */
    @TableField("run_id")
    private String runId;

    /**
     * 工程清单变更主键
     */
    @TableId(type = IdType.INPUT)
    private String boqcId;

    @TableField("Con_Id")
    private String conId;
    /**
     * 标题
     */
    @TableField("Boqc_Title")
    private String boqcTitle;

    /**
     * 备注
     */
    @TableField("Boqc_Remark")
    private String boqcRemark;
    /**
     * 版本号
     */
    @TableField("Boqc_Version")
    private String boqcVersion;
    /**
     * 项目ID
     */
    @TableField("Boqc_Product_Id")
    private String boqcProductId;

    /**
     * 项目名
     */
    @TableField("Boqc_Product_Name")
    private String boqcProductName;

    /**
     * 合同ID
     */
    @TableField("Boqc_Contract_Id")
    private String boqcContractId;
    /**
     * 合同名
     */
    @TableField("Boqc_Contract_Name")
    private String boqcContractName;

    /**
     * 创建人ID
     */
    @TableField("Boqc_Create_Uid")
    private String boqcCreateUid;
    /**
     * 创建人姓名
     */
    @TableField("Boqc_Create_Uname")
    private String boqcCreateUname;

    /**
     * 创建时间
     */
    @TableField(value = "Boqc_Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqcCreateTime;
    /**
     * 更新时间
     */
    @TableField(value = "Boqc_Update_Time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqcUpdateTime;

    /**
     * 审批状态
     */
    @TableField("Boqc_State")
    private Integer boqcState;
    /**
     * 审批时间
     */
    @TableField("Boqc_State_Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqcStateTime;

    @TableField("P_Id")
    @JsonProperty("pId")
    private String pId;

    @TableField(exist = false)
    private List<ConBoqDtlChange> dtlPojoList;
    @TableField(exist = false)
    private String rteFileIds;
    @TableField(exist = false)
    private String fileRelationId;
    private String teId;
    private String conBoqVersion;
    private String conBoqType;
    private String boqId;
    private Integer version;
    private String reason;
}
