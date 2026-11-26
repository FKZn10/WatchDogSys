package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 招标清标
 */
@Data
@TableName("Sec_Bright_Moon")
public class SecBrightMoon {
    @TableId(type = IdType.INPUT)
    private String sbmId;
    private String secId;
    private Date sbmStartTime;
    private Date sbmEntTime;
    private String sbmBidClearanceReport;
    private String sbmListBidClearingTeams;
    private String sbmContractorInformation;
    private String sbmCreateUid;
    private String sbmCreateUname;
    private Date sbmCreateTime;
    private Integer sbmState;
    private Date sbmStateTime;
    private String teId;
}
