package com.jpmsoft.common.model.response.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.request.SecInfo.SecReviewMaterialRequest;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
付款请审
 */
@Data
public class PaymentReviewResponse extends BaseFileRequest {

    private String 	id;
    private String 	pId;
    private String 	pName;
    private String 	prCode;
    private String 	applyUnit;
    private String 	createUname;
    private String 	conId;
    private String 	conName;
    private Date stateTime;
    private String 	applyContent;
    private String 	createUid;
    private String 	applyType;
    private Date 	createTime;
    private Integer state;

    private String cId;

    private BigDecimal bringTrialMoney;

    private BigDecimal firstTrialMoney;

    private BigDecimal reviseReduceMoney;

    private BigDecimal conMoneyTotal;

    @JsonProperty("cName")
    private String cName;

    private List<SecReviewMaterialResponse> reviewMaterialList;
}
