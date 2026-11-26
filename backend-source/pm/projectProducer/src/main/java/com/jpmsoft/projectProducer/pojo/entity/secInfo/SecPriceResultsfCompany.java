package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 询价单位
 */
@Data
@TableName("Sec_Price_Resultsf_Company")
public class SecPriceResultsfCompany {

    @TableId(type = IdType.INPUT)
    private String sprcId;
    private String sprId;
    private String sprcCid;
    private String sprcCname;
    private String sprcContacts;
    private String sprcContactNumber;
    private String sprcRemarks;
    private BigDecimal sprcMoney;
    private String sprcType;
    private String sprcOtherPricing;

}
