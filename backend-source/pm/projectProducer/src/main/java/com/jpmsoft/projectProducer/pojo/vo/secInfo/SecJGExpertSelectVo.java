package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SecJGExpertSelectVo extends BaseFileResponse implements Serializable {


    private static final long serialVersionUID = 3700832155787673280L;

    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    //技术类人数（内部）
    private Integer esInsideTechnologyNum;
    //经济类人数（内部）
    private Integer esInsideEconomicsNum;
    //专家总人数（内部）
    private Integer esInsideExpertNum;
    //技术类专家总人数（内部）
    private Integer esInsideTechnologySum;
    //经济类专家总人数（内部）
    private Integer esInsideEconomicsSum;
    //技术类人数（外部）
    private Integer esOutsideTechnologyNum;
    //经济类人数（外部）
    private Integer esOutsideEconomicsNum;
    //专家总人数（外部）
    private Integer esOutsideExpertNum;
    //技术类专家总人数（外部）
    private Integer esOutsideTechnologySum;
    //经济类专家总人数（外部）
    private Integer esOutsideEconomicsSum;
    //技术类人数
    private Integer esTechnologyNum;
    //经济类人数
    private Integer esEconomicsNum;
    //专家总人数
    private Integer esExpertNum;
    //技术类专家总人数
    private Integer esTechnologySum;
    //经济类专家总人数
    private Integer esEconomicsSum;
    private String esRemark;
    private String esCompany;
    private String esContact;
    private String esTel;
    private String esBusiness;
    private String esMode;
    private String esCategory;
    private String esType;
    private Date esFilingTime;
    private String esLocation;
    private Date esBidTime;
    private String esOtherTxt;
    private String esExList;

    private String esCompanyName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    //概算金额
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    //标段名称
    private String secName;
    //招标人Id
    private String secTenderee;
    //代理单位ID
    private String secAgency;
    //招标人(单位)
    private String secTendereeName;
    //代理单位
    private String secAgencyName;
    //招标方式
    private String secBiddingType;
    //组织形式
    private String secOrganizeForm;
    //资格审查方式
    private String secQualiReview;
    //是否评定分离
    private Boolean secEvaluateSeparation;
    //开标地点
    private String secLoaction;
    //开标时间
    private Date secBidTime;
    //投资人资格要求
    private String secInvestorsQuali;
    //备注(其他要求)
    private String secRemark;
    //建设地点
    private String secPlace;
    private String secType;
    //评标方法
    @JsonProperty("dFunction")
    private String dFunction;
    //招标范围
    @JsonProperty("dScope")
    private String dScope;
    //工期要求
    @JsonProperty("dExecutiveStandard")
    private String dExecutiveStandard;
    //招标控制价
    @JsonProperty("dAmount")
    private String dAmount;
    //开标时间
    @JsonProperty("dBidTime")
    private String dBidTime;
    //开标地点
    @JsonProperty("dLocation")
    private String dLocation;
    /**
     * 专家选取记录ID
     */
    private String srId;
    /**
     * 专家选取记录流程状态
     */
    private Integer srState;
}
