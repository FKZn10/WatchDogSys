package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Agreement")
public class Agreement {
    // 主键ID
    @TableField(value = "id")
    private String id;

    // 创建用户UID
    @TableField(value = "create_uid")
    private String createUid;

    // 创建用户名
    @TableField(value = "create_uname")
    private String createUname;

    // 创建时间
    @TableField(value = "create_time")
    private Date createTime;

    // 状态
    @TableField(value = "state")
    private Integer state;

    // 状态变更时间
    @TableField(value = "state_time")
    private Date stateTime;

    // 父ID
    @TableField(value = "p_id")
    private String pId;

    // 合同ID
    @TableField(value = "con_id")
    private String conId;

    // 监管代码
    @TableField(value = "a_supervisory_code")
    private String aSupervisoryCode;

    // 银行代码
    @TableField(value = "a_bank_code")
    private String aBankCode;

    // 承包商ID
    @TableField(value = "a_contractor_id")
    private String aContractorId;

    // 承包商业务范围
    @TableField(value = "a_contractor_business")
    private String aContractorBusiness;

    // 承包商负责人
    @TableField(value = "a_contractor_charge")
    private String aContractorCharge;

    // 承包商地址
    @TableField(value = "a_contractor_domicile")
    private String aContractorDomicile;

    // 承包商邮编
    @TableField(value = "a_contractor_postcode")
    private String aContractorPostcode;

    // 承包商电话
    @TableField(value = "a_contractor_phone")
    private String aContractorPhone;

    // 外包商ID
    @TableField(value = "a_outsourcing_id")
    private String aOutsourcingId;

    // 外包商业务范围
    @TableField(value = "a_outsourcing_business")
    private String aOutsourcingBusiness;

    // 外包商负责人
    @TableField(value = "a_outsourcing_charge")
    private String aOutsourcingCharge;

    // 外包商地址
    @TableField(value = "a_outsourcing_domicile")
    private String aOutsourcingDomicile;

    // 外包商邮编
    @TableField(value = "a_outsourcing_postcode")
    private String aOutsourcingPostcode;

    // 外包商电话
    @TableField(value = "a_outsourcing_phone")
    private String aOutsourcingPhone;

    // 监管银行
    @TableField(value = "a_regulatory_bank")
    private String aRegulatoryBank;

    // 监管负责人
    @TableField(value = "a_regulatory_charge")
    private String aRegulatoryCharge;

    // 监管地址
    @TableField(value = "a_regulatory_domicile")
    private String aRegulatoryDomicile;

    // 监管邮编
    @TableField(value = "a_regulatory_postcode")
    private String aRegulatoryPostcode;

    // 监管电话
    @TableField(value = "a_regulatory_phone")
    private String aRegulatoryPhone;

    // 监管账户
    @TableField(value = "a_supervisory_account")
    private String aSupervisoryAccount;

    // 监管账号
    @TableField(value = "a_supervisory_number")
    private String aSupervisoryNumber;

    // 监管开户行
    @TableField(value = "a_supervisory_bank")
    private String aSupervisoryBank;

    // 预留类型
    @TableField(value = "a_reserved_type")
    private String aReservedType;

    // 解除状态
    @TableField(value = "a_relieve")
    private Integer aRelieve;

    // 状态
    @TableField(value = "a_state")
    private Integer aState;

    // 是否解除
    @TableField(value = "a_is_relieve")
    private String aIsRelieve;

    // 解除ID
    @TableField(value = "a_relieve_id")
    private String aRelieveId;
}
