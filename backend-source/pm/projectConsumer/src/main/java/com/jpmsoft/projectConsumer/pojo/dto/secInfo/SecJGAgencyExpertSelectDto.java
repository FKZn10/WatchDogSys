package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class SecJGAgencyExpertSelectDto extends BaseFileDto {

    private static final long serialVersionUID = -5225951741635175392L;

    @JsonProperty("pId")
    private String pId;
    private String secId;
    private Integer esTechnologyNum;
    private Integer esEconomicsNum;
    private Integer esExpertNum;
    private String esRemark;
    private String esCompany;
    private String esContact;
    private String esTel;
    private String esBusiness;
    private String esMode;
    private String esCategory;
    private String esType;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date esFilingTime;
    private String esLocation;
    private Date esBidTime;
    private String esOtherTxt;
    private String esExList;
    private Integer esTechnologySum;
    private Integer esEconomicsSum;

    private String esCompanyName;
    @JsonProperty("pName")
    private String pName;
    //标段名称
    private String secName;
}
