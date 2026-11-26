package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标段资金来源
 */
@Data
@TableName("Sec_Sources_Funds")
public class SecSourcesFunds {

    @TableId(type = IdType.INPUT)
    private String ssfId;
    private String secId;
    private String ssfCode;
    private Date ssfCreateTime;
    private Float ssfScale;
    private BigDecimal ssfMoney;
    private String ssfRemark;
    private Integer ssfSort;
    private String ssfCard;
    private String ssfAccount;
    private String ssfName;
    private String ssfSubject;
    private String ssfDepartment;
    private String ssfHead;
}
