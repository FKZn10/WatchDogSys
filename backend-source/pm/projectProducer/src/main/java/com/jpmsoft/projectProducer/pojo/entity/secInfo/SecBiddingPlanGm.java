package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/12/26 11:11
 */
@Data
@TableName("Sec_Bidding_Plan_GM")
public class SecBiddingPlanGm extends BaseEntity {

    private String bpDid;
    private Date bpSecTime;
    private Date bpEndTime;
    private String bpCpsx;
    private BigDecimal bpMoney;
    private String secId;
    private String bpXmgk;
}
