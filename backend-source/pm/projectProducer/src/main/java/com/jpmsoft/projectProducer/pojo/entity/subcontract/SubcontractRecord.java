package com.jpmsoft.projectProducer.pojo.entity.subcontract;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包合同备案
 */
@Data
@TableName("Subcontract_Record")
public class SubcontractRecord {

    @TableId(type = IdType.INPUT)
    private String scId;
    private String scTitle;
    private String scName;
    private String scCode;
    private BigDecimal ScMoney;
    private String scMode;
    private String scFp;
    private String scSp;
    private String scSpDeputy;
    private String scSpDelegator;
    private String scSpDelegatorSsn;
    private Date scDate;
    private String scResident;
    private String scResidentTel;
    private String scResidentSsn;
    private BigDecimal scColPaid;
    private String scColAccount;
    private String scColAccountHolder;
    private String scSelectMethod;
    private String scParticipationBid;
    private String scBidMethod;
    private String scBidExpert;
    private String scCreateUid;
    private String scCreateUname;
    private Date scCreateTime;
    private Integer scState;
    private Date scStateTime;
    private String scType;
    private String pId;
    private String conId;
    private String scSup;
    private String scMasterConId;
    private String scSupIsChangeMoney;
    private Integer scSupChangeMonType;
    private BigDecimal scSupChangeMoney;
    private String scSupIsChangeDuration;
    private Integer scSupChangeDurType;
    private Integer scSupChangeDuration;
    private String teId;
}
