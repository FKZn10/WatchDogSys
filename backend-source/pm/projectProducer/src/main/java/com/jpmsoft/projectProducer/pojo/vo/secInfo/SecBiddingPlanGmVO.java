package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/12/26 11:11
 */
@Data
public class SecBiddingPlanGmVO implements Serializable {

    private static final long serialVersionUID = -6552073132161188090L;
    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String bpDid;
    private Date bpSecTime;
    private Date bpEndTime;
    private String bpCpsx;
    private BigDecimal bpMoney;
    private String secId;
    private String bpXmgk;

    private String secName;
    private String bpDName;
}
