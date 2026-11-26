package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标保证金
 */
@Data
@TableName("Sec_Bid_Security")
public class SecBidSecurity {

    @TableId(type = IdType.INPUT)
    private String sbsId;
    private String secId;
    private String sbsTitle;
    private String sbsRemarks;
    private Integer sbsNumberEnterprises;
    private BigDecimal sbsCumulativePaymentAmount;
    private String sbsCreateUid;
    private String sbsCreateUname;
    private Date sbsCreateTime;
    private Integer sbsState;
    private Date sbsStateTime;
    private String teId;
}
