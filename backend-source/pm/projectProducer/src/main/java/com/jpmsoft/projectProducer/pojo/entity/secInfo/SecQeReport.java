package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 资格审查报告
 */
@Data
@TableName("Sec_QE_Report")
public class SecQeReport {
    @TableId(type = IdType.INPUT)
    private String qerId;
    private String secId;
    private Date qerDate;
    private Integer qerBidderNum;
    private Integer qerBidderNumAdopt;
    private String qerResult;
    private String qerReport;
    private String qerCreateUid;
    private String qerCreateUname;
    private Date qerCreateTime;
    private Integer qerState;
    private Date qerStateTime;
    private String qerRemark;
    private String teId;
}
