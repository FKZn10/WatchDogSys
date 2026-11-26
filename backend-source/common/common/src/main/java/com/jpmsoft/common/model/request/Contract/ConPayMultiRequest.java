package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConPayMultiRequest extends BaseFileRequest {
    private String id;
    private String ccmId;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String name;
    private String code;
    private String paymentType;
    private String paymentName;
    private String payeeUnit;
    private String payeeUnitName;
    private Date payTime;
    private Integer payPeriod;
    private Integer payTotalPeriods;
    private BigDecimal contractAmount;
    private BigDecimal paidAmount;
    private BigDecimal paidRatio;
    private BigDecimal currentPayAmount;
    private BigDecimal changeVisaPaidAmount;
    private BigDecimal visaTempFundRatio;
    private BigDecimal safetyFeePaidAmount;
    private BigDecimal workerWagePaidAmount;
    private BigDecimal workerWageAmount;
    private BigDecimal projectAmount;
    private BigDecimal projectPaidAmount;
    private String payBasis;
    private Date documentPreparationdata;

    private List<ConPayMultiProjectRequest> conPayMultiProject;
    private List<ConPayMultiFundingRequest> conPayMultiFunding;
}