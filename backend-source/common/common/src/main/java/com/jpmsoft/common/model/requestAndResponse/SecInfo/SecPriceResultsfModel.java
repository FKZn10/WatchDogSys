package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 巡价结果
 */

@Data
public class SecPriceResultsfModel extends BaseFileRequest {

    private String sprId;
    private String secId;
    private String sprRemarks;
    private String sprCreateUid;
    private Date sprCreateTime;
    private String sprCreateUname;
    private Integer state;
    private Date stateTime;
    private String sprCompany;
    private String sprPrerequisites;
    private BigDecimal sprAmt;
    private BigDecimal sprPriceCeiling;
    private BigDecimal sprConMoney;

    private String secName;
    private String secCode;
    private String secType;
    private String secBiddingType;
    private String secTendereeName;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String secOrganizeForm;
    private String sitScope;
    private List<SecPriceResultsfCompanyModel> prCompanyModels;
    private List<SecProjectResponse> secProjects;
    //单位简称
    private String secTendereeAbbName;
    private String sprCompanyName;
    private String sprCompanyAbbName;
    private String secTradingPlace;
    private List<String> secStartTimes;
    private List<String> secBiddingCycles;
    private List<String> sprConMoneys;
    private List<String> sprPriceCeilings;
    private List<String> sprAmts;
    private List<String> sprCreateTimes;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    private String seResult;
    @JsonProperty("pName")
    private String pName;
    private String secAgencyName;


    /**
     * 中选单位
     */
    private String sprSelectUnit;

    private String sprSelectUnitName;

    /**
     * 中选金额
     */
    private BigDecimal sprSelectAmt;

}
