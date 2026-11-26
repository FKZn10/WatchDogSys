package com.jpmsoft.projectProducer.pojo.entity.subcontract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包单位备案
 */
@Data
@TableName("Subcontractor_Record")
public class SubcontractorRecord {

    @TableId(type = IdType.INPUT)
    private String srId;
    private String srTitle;
    private String srUnit;
    private String srBusinessLicense;
    private String srType;
    private String srSpecialty;
    private BigDecimal srRegisteredCapital;
    private String srQualificationType;
    private String srQualificationLevel;
    private String srQualificationCertificate;
    private String srRpName;
    private String srRpIdcard;
    private String srRpPost;
    private String srRpTel;
    private String srAchievement;
    private String srAchievementProve;
    private String srCreateUid;
    private String srCreateUname;
    private Date srCreateTime;
    private Integer srState;
    private Date srStateTime;
    private String srIdcardScan;
    private String srUnitName;
    private String srCreditCode;
    private String srUnitType;
    private String teId;

}
