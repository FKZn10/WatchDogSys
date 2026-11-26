package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/31 11:00
 */
@Data
public class SecEntrustDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = 617336408885182974L;
    //标段ID
    private String secId;
    private String seResult;
    //委托单位ID
    private String seCpyId;
    private BigDecimal seAmt;
    private String sePm;
    private String seQualification;
    private String seAchievement;
    private String seRemark;

    private String secName;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private String tendereeName;

    private String secTradingPlace;
    private String secPlace;
    private String seCpyName;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pName")
    private String pName;
    private List<String> createTimes;
    private List<String> seAmts;
    private List<String> secStartTimes;
    private List<String> secBiddingCycles;


}
