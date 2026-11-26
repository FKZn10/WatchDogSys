package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 09:31
 * @TableName CalibrationReport
 */
@Data
public class CalibrationReportVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -8627566336872024552L;

    private String id;
    private String secId;
    private String crTitle;
    private String crOriginator;
    private String crContent;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date stateTime;
    private Integer state;
    private String crCode;
    private String crBidWinner;
    private String crPricingMethod;
    private BigDecimal crMoney;
    private String crOtherValuation;
    private Boolean crBacmApprove;
    private String crCIdOne;
    private String crOfferOne;
    private String crArgumentOne;
    private String crProfileOne;
    private String crCIdTwo;
    private String crOfferTwo;
    private String crArgumentTwo;
    private String crProfileTwo;
    private String crCIdThree;
    private String crOfferThree;
    private String crArgumentThree;
    private String crProfileThree;


    private String secName;
    private String secCode;
    private String crCNameOne;
    private String crCNameTwo;
    private String crCNameThree;
    private String crIsClarification;
    private String crClarificationFile;
    private String crClarificationFileIds;
}