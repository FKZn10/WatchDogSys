package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 支付申请表子表
 * 户名 账号
 */
@Data
@TableName("Con_Payfunds_Dtl")
public class ConPayfundsDtl {

    private String id;

    //经费账户户名
    private String payfundaccount;

    //经费账户账号
    private String payfundaccountnum;

    // 合同支付表id
    @TableField(value = "PayDtlId")
    private String PayDtlId;

    private String payfundaccountmoney;
}
