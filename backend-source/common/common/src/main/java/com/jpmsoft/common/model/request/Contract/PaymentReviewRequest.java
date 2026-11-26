package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.request.SecInfo.SecReviewMaterialRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
付款请审
 */
@Data
public class PaymentReviewRequest extends BaseFileRequest {

    private String 	id;
    private String 	projectId;
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

    private List<SecReviewMaterialRequest> reviewMaterialList;
}
