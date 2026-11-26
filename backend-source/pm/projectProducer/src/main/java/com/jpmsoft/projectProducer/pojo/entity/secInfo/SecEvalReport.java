package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 评标报告
 */
@Data
@TableName("Sec_Eval_Report")
public class SecEvalReport {

    @TableId(type = IdType.INPUT)
    private String erId;
    private String secId;
    private String erMethod;
    private Date erDate;
    private String erPlace;
    private String erCreateUid;
    private String erCreateUname;
    private Date erCreateTime;
    private Integer erState;
    private Date erStateTime;
    private BigDecimal erRate;
    private String erOtherValuation;

    private String erDeterminType;
    private BigDecimal erBidWinningPrice;
    private String erArmorApplication;
    private String erCandidateUnit;
}
