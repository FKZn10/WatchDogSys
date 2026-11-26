package com.jpmsoft.projectProducer.pojo.entity.bankAccount;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 单位账户管理
 */
@Data
@TableName("Bank_Account")
public class BankAccount {

    @TableId(type = IdType.INPUT)
    private String baId;
    private String baCId;
    private String baType;
    private String baName;
    private String baNu;
    private String baBank;
    private String baBankName;
    private String baBankNu;
    private String baCreateUid;
    private String baCreateUname;
    private Date baCreateTime;
    private Integer baState;
    private Date baStateTime;
    @JsonProperty("pId")
    @TableField(value = "P_Id")
    private String pId;
    private String teId;
}
