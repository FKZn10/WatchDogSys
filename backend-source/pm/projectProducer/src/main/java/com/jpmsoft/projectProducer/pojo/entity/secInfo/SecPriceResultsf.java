package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 询价结果
 */
@Data
@TableName("Sec_Price_Resultsf")
public class SecPriceResultsf {

    @TableId(type = IdType.INPUT)
    private String sprId;
    private String secId;
    private String sprRemarks;
    private String sprCreateUid;
    private Date sprCreateTime;
    private String sprCreateUname;
    private Integer state;
    private Date stateTime;
    //中标单位
    private String sprCompany;
    //资格条件
    private String sprPrerequisites;
    //中标金额
    private BigDecimal sprAmt;
    //最高限价
    private BigDecimal sprPriceCeiling;
    //合同金额
    private BigDecimal sprConMoney;
    private String teId;
}
