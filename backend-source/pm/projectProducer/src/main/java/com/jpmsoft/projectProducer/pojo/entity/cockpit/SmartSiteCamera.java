package com.jpmsoft.projectProducer.pojo.entity.cockpit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 摄像头信息
 */
@Data
@TableName("Smart_Site_Camera")
public class SmartSiteCamera {

    @TableId(type = IdType.INPUT)
    private String sscId;
    private String pId;
    private String sscName;
    private String sscUrl;
    private String sscPosition;
    private String sscType;
    private String sscRemark;
    private String sscCreateUid;
    private String sscCreateUname;
    private Date sscCreateTime;
    private Integer sscState;
    private String sscKind;
    private String sscPlaybackAddress;
    private String sscCode;
    private String sscOverviewDisplay;
    private String sscNumber;
    private String sscKey;
    private String sscSecret;
    private String sscSerial;
    private String sscChannelNo;
    private String teId;

}
