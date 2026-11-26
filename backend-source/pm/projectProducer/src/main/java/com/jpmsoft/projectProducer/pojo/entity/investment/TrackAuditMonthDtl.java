package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跟踪审计月报
 */
@Data
@TableName("Track_Audit_Month")
public class TrackAuditMonthDtl {
    private String tamId;
    private String pId;
    private String trackCId;
    private String conId;
    private Date monthDate;
    private BigDecimal completeValue;
    private BigDecimal totleValueNotThis;

    private BigDecimal totleValue;
    private BigDecimal totleRate;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private String updateUserId;
    private String updateUserName;
    private Date updateTime;
    private Integer state;
    private Date stateTime;
    private Integer dataStatus;
}
