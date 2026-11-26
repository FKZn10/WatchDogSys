package com.jpmsoft.projectProducer.pojo.entity.conBoq;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("Con_Boq")
public class ConBoq {

    /**
     * AI识别后台任务ID。
     */
    @TableField("run_id")
    private String runId;

    /**
     * 工程清单主键
     */
    @TableId(type = IdType.INPUT)
    private String boqId;

    @TableField("Con_Id")
    private String conId;
    /**
     * 标题
     */
    @TableField("Boq_Title")
    private String boqTitle;

    /**
     * 备注
     */
    @TableField("Boq_Remark")
    private String boqRemark;
    /**
     * 版本号
     */
    @TableField("Boq_Version")
    private String boqVersion;
    /**
     * 项目ID
     */
    @TableField("Boq_Product_Id")
    private String boqProductId;

    /**
     * 项目名
     */
    @TableField("Boq_Product_Name")
    private String boqProductName;

    /**
     * 合同ID
     */
    @TableField("Boq_Contract_Id")
    private String boqContractId;
    /**
     * 合同名
     */
    @TableField("Boq_Contract_Name")
    private String boqContractName;

    /**
     * 创建人ID
     */
    @TableField("Boq_Create_Uid")
    private String boqCreateUid;
    /**
     * 创建人姓名
     */
    @TableField("Boq_Create_Uname")
    private String boqCreateUname;

    /**
     * 创建时间
     */
    @TableField(value = "Boq_Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqCreateTime;
    /**
     * 更新时间
     */
    @TableField(value = "Boq_Update_Time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqUpdateTime;

    /**
     * 审批状态
     */
    @TableField("Boq_State")
    private Integer boqState;
    /**
     * 审批时间
     */
    @TableField("Boq_State_Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqStateTime;

    @TableField("P_Id")
    @JsonProperty("pId")
    private String pId;

    @TableField(exist = false)
    private List<ConBoqDtl> dtlPojoList;
    @TableField(exist = false)
    private String rteFileIds;
    @TableField(exist = false)
    private String fileRelationId;
    private String teId;
    /**
     * 清单版本。
     */
    private String conBoqVersion;
    /**
     * 清单类型。
     */
    private String conBoqType;
}
