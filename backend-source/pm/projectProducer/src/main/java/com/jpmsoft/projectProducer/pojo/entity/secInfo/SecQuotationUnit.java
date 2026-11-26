package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:49
 */
@Data
@TableName("Sec_Quotation_Unit")
public class SecQuotationUnit {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String sirId;
    //单位ID
    @JsonProperty("cId")
    private String cId;
    //报价金额
    private BigDecimal squAmt;
    //报价时间
    private Date squTime;
    //资质等级
    private String squQualificationLevel;
    //联系人
    private String squContacts;
    //联系电话
    private String squContactNumber;
    //备注
    private String squRemark;
    //序号
    private Integer squSort;
    //附件
    private String squFile;
}
