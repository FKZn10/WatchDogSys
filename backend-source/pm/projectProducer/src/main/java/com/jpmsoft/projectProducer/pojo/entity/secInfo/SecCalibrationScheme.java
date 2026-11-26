package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 定标准备方案
 */
@Data
@TableName("Sec_Calibration_Scheme")
public class SecCalibrationScheme {

    @TableId(type = IdType.INPUT)
    private String scsId;
    private String secId;
    private Date scsBidClosingTime;
    private String scsApprovalDescription;
    private String scsMinutesMeeting;
    private String scsCalibrationScheme;
    private String scsCreateUid;
    private String scsCreateUname;
    private Date scsCreateTime;
    private Integer scsState;
    private Date scsStateTime;
    private String teId;
}
