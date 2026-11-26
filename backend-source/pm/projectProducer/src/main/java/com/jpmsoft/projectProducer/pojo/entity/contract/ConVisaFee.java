package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签证费用申报
 */
@Data
@TableName("Con_Visa_Fee")
public class ConVisaFee {

    @TableId(type = IdType.INPUT)
    private String vfId;
    private String vrId;
    private String vfCode;
    private String vfLocation;
    private String vfDrawingNo;
    private String vfReason;
    private String vfContent;
    private String vfContractor;
    private String vfType;
    private BigDecimal vfMoney;
    private Integer vfDuration;
    private String vfResponsibleParty;
    private String vfGrade;
    private String vfRemark;
    private String vfCreateUid;
    private String vfCreateUname;
    private Date vfCreateTime;
    private Integer vfState;
    private Date vfStateTime;
    private String pId;
    private String conId;

    private String vfFollowUnit;
    private String vfDesignUnit;
    private String vfSupUnit;
    private String vfProposer;
    private String vfAmountInvolved;
    private String vfVisaCategory;
    private String vfScope;
    private String vfFileContact;
    private String vfFileEstimate;
    private String teId;
    private String svisId;

    private String vfMoneyType;

    private Date vfDate;

    private String vfProjectManager;

    private BigDecimal vfPositiveAmount;

    private BigDecimal vfTotalMoney;

    private BigDecimal vfNegativeVariable;
    private String vfIsHistory;

    private String cmnId;

    // 预算费用
    private BigDecimal vfBudgetMoney;

    // 申请估算金额
    private BigDecimal vfApplyMoney;

    private String clId;

    /**
     *     区分 暂列金 01 默认 或者 暂估价 02
     */
    private String vfDataSeparateType;

}
