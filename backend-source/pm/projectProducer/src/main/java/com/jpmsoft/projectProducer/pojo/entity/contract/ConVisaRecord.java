package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签证计量备案
 */
@Data
@TableName("Con_Visa_Record")
public class ConVisaRecord {

    @TableId(type = IdType.INPUT)
    private String vrId;
    private String conId;
    private String vrCode;
    private Date vrTime;
    private String vrPlace;
    private String vrContent;
    private String vrParticipant;
    private String vrRemark;
    private String vrCreateUid;
    private String vrCreateUname;
    private Date vrCreateTime;
    private Integer vrState;
    private Date vrStateTime;
    private String vrPic;
    private String vrClId;
    private String pId;
    private BigDecimal vrAmountInvolved;
    private BigDecimal vrEstimatedAmount;
    private String vrFollowUnit;
    private String vrDesignUnit;
    private String vrSupUnit;
    private String vrProposer;
    private String vrPosition;
    private String vrInfluenceTime;
    private String vrVisaCategory;
    private String vrScope;
    private String vrReason;
    private String vrFileContact;
    private String vrFileEstimate;
    private String teId;

    private String enisId;

    private String vrName;
    private String vrCid;
    private String vrDid;
    private String vrCnature;
}
