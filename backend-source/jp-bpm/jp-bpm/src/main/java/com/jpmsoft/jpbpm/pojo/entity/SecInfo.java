package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标段信息
 */
@Data
@TableName("Sec_Info")
public class SecInfo {

    @TableId(type = IdType.INPUT)
    private String secId;
    private String secName;
    private String secCode;
    private String secParentId;
    private Integer secYear;
    private String secBiddingType;
    private String secConType;
    private String secType;
    private BigDecimal secMoney;
    private Date secStartTime;
    private Date secEndTime;
    private String secRemark;
    private Date secFlowTime;
    private Integer secFlowState;
    private String secCreateUid;
    private String secCreateUname;
    private Date secCreateTime;
    private String secPid;
    private String secTenderee;
    private String secFeatures;
    private String secPlace;
    private String secScale;

    private String bpId;


}
