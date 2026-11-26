package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 定标情况报告
 */
@Data
@TableName("Sec_Calibration_Report")
public class SecCalibrationReport {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private String secId;
    private Date scrDate;
    @TableField("ScR_method")
    private String scRMethod;
    private Double bidPrice;
    private BigDecimal tenderPrice;
    @TableField("Bid_WInner")
    private String bidWInner;
    private Integer totalVotes;
    private Integer ownVotes;
    private String scrRemarks;
}