package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 来款认领 实体类
 *
 * @author tianhelin
 * @date 2025/11/17
 */
@Data
@TableName("Claim_Incoming_Funds")
public class ClaimIncomingFunds {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 创建人ID
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审批状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 交易金额
     */
    private BigDecimal cifMoney;

    /**
     * 对方账号
     */
    private String cifAccount;

    /**
     * 对方账户
     */
    private String cifCa;

    /**
     * 交易时间
     */
    private Date cifDate;

    /**
     * 摘要
     */
    private String cifAbstract;

    /**
     * 用途
     */
    private String cifPurpose;

    /**
     * 附言
     */
    private String cifPostscript;

    private String pId;

    private String cifIsReceipt;

    private String cifIsWriteOff;

    private String cifReceipt;

    /**
     * 是否认领
     */
    private String cifIsIncoming;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人ID
     */
    private String updateUserId;

    /**
     * 更新人名称
     */
    private String updateUserName;

    private String cifVerifyState;

    /**
     * 认领单位名称
     */
    @TableField("cif_C_Name")
    private String cifCName;

    /**
     * 认领单位Id
     */
    @TableField("cif_C_Id")
    private String cifCId;
}