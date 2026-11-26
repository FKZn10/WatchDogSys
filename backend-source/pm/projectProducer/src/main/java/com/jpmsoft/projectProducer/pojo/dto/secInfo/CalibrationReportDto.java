package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
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
public class CalibrationReportDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 496378254314264796L;

    /**
     *
     */
    private String id;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    /**
     *
     */
    private String secId;

    /**
     *
     */
    private String crTitle;

    /**
     *
     */
    private String crOriginator;

    /**
     *
     */
    private String createUid;

    /**
     *
     */
    private String createUname;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date stateTime;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private String crContent;
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

    private String crIsClarification;
    private String crClarificationFile;
    private String crClarificationFileIds;

    private String crProfileIdsOne;

    private String crProfileIdsTwo;

    private String crProfileIdsThree;
}