package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:24
 */
@Data
public class SecSolicitationNoticeDto extends BaseFileDto {

    private static final long serialVersionUID = 1230587565900263387L;

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
    private String  snCalibration;
    private String  snRequirementBasis;

    private String snProcureCpyName;
    private String secName;
}
