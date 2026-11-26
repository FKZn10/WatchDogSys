package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 征集公告
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:24
 */
@Data
@TableName("Sec_Solicitation_Notice")
public class SecSolicitationNotice extends BaseEntity {
    private String secId;
    private String snTitle;
    private String snProjectOverview;
    private String snContractContent;
    private Date snAnnouncementStartTime;
    private Date snAnnouncementEndTime;
    private Date snDeadline;
    private String snQuotationMethod;
    private BigDecimal snProvisionalPricing;
    private String snSettlementMethod;
    private String snProjectAddress;
    private String snApplicantCondition;
    private String snPersonCharge;
    private Boolean snJointRegistrationf;
    private String snFileCondition;
    private BigDecimal snControlPrice;
    private Float snFloatationRate;
    private String snAgencyCpy;

    private String snApprovalMatter;
    private String snProcurePerson;
    private String snProcurePhone;
    private BigDecimal snQuotation;
    private String snProcureCpy;
    private String teId;

    private String  snCalibration;
    private String  snRequirementBasis;
}
