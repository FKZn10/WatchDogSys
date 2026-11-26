package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 主要项目管理人员
 */
@Data
@TableName("Sec_Win_Unit_Member")
public class SecWinUnitMember {
    @TableId(type = IdType.INPUT)
    private String wumId;
    private String wnId;
    private String wumName;
    private String wumMobilePhone;
    private Integer wumOutland;
    private String wumSocialSecurityNum;
    private String wumPosition;
    private String wumEducation;
    private String wumTitle;
    private String wumCertificateLevel;
    private String wumCertificateNum;
    private String wumIdCard;
    private Date wumCheckOnDate;
    private String wumRemark;
    private Integer wumDefence;
    private Integer wumSort;
    private Date wumCreateTime;
    private String wumPositionCode;
}
