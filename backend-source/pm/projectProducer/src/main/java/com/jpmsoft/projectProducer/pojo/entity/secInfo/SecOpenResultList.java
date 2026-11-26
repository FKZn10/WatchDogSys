package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 开标结果清单
 */
@Data
@TableName("Sec_Open_Result_List")
public class SecOpenResultList {
    @TableId(type = IdType.INPUT)
    private String soplId;
    private String orId;
    private String soplBidder;
    private String soplUnifiedCode;
    private BigDecimal soplOffer;
    private BigDecimal soplSafeCivilizedConcost;
    private BigDecimal soplBattleAmount;
    private String soplQualityStandard;
    private String soplDuration;
    private String soplStandardizedManage;
    private String soplElectronicOpening;
    private String soplAnyObjection;
    private BigDecimal soplMaximumPrice;
    private BigDecimal soplFloatingCoefficient;
    private String soplBiddersRepresentative;
    private Integer soplRequiredDuration;
    private String soplTendereesRepresentative;
    private String soplNotetaker;
    private String soplBidSupervisor;
}
