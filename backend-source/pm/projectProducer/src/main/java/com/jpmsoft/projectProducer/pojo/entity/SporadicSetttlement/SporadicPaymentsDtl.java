package com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("sporadic_payments_dtl")
public class SporadicPaymentsDtl {

    @TableField("id")
    private String id;

    /**
     * 保修项目名称
     */
    @TableId("sp_d_pname")
    private String spDName;

    /**
     * 报修项目id
     */
    @TableId("sp_d_pid")
    private String spDPid;

    /**
     * 流水号
     */
    @TableId("sp_d_nuber")
    private String spDNuber;

    /**
     * 审核价
     */
    @TableId("sp_d_auditprice")
    private BigDecimal spDAuditprice;

    /**
     * 备注
     */
    @TableId("sp_d_remarks")
    private String spDRemarks;

    /**
     * 关联结算单id
     */
    @TableId("sp_d_id")
    private String spDId;

    /**
     * 关联主表id
     */
    @TableId("sp_p_did")
    private String spPDid;
}
