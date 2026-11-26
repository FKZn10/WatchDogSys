package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同计量计价 请求参数
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConMeasurePriceRequest extends BaseFileRequest {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String conId;
    private String conName;
    private Integer cmpPeriod;
    private Date cmpDeclareStartTime;
    private Date cmpDeclareEndTime;
    private BigDecimal cmpCurrentAmount;
    private BigDecimal cmpAccumulatedAmount;
    private BigDecimal cmpPayableAmount;
    private BigDecimal cmpDeductibleAmount;
    private BigDecimal cmpActualPayment;
    private String cmpRemark;
    private String cmpType;
    private String filterUsed;
    private String filterUsedGfp;
    private String payId;
    private String gfpId;
}
