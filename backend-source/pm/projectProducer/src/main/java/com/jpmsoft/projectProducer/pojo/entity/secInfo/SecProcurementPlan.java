package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招采计划
 */
@Data
@TableName("Sec_Procurement_Plan")
public class SecProcurementPlan {

    @TableId(type = IdType.INPUT)
    private String ppId;

    //项目id
    private String pId;

    //计划名称
    private String planName;

    //计划编码
    private String planCode;

    //计划类型  JPMsoft_JHLX
    private String planType;


    /**
     * 计划状态  JPMsoft_JHZT
     */
    private String planStatus;

    /**
     * 估算金额
     */
    private BigDecimal estimatedAmount;

    /**
     * 计划形式  JPMsoft_JHXS
     */
    private String planForm;


    /**
     * 招标方式  JPMsoft_ZBFS
     */
    private String biddingMethod;

    /**
     * 计划开始时间
     */
    private Date planStartTime;


    /**
     * 工作内容
     */
    private String jobContent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    @TableField(value = "Create_Time",fill = FieldFill.INSERT)
    private Date createTime;

    private Integer state;

    private Date stateTime;

    private String updateUid;

    @TableField(value = "Update_Time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String teId;
}
