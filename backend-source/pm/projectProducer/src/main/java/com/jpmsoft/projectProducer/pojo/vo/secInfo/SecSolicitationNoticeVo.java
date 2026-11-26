package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:24
 */
@Data
public class SecSolicitationNoticeVo extends BaseFileResponse implements Serializable {


    private static final long serialVersionUID = -8582963882827967055L;
    private String id;
    private String snTitle;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String secId;
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
    private String secTendereeName;
    private String secContractingDepartmentName;
    private String secName;
    private String secType;
    private List<SecProjectResponse> secProjects;
}
