package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yb.Sun
 * @date 2024/11/14 10:18
 */
@Data
public class SecJGSelectionRecordPrintVo {
    private String id;
    //会议/咨询类型
    private String srType;
    //选取时间
    private Date srAccessTime;
    //会议事项简介
    private String srAbout;
    //会议开始时间
    private Date srStart;
    //选取人所属主管机构
    private String srCompetent;
    //咨询事项
    private String srItems;
    //资料清单
    private String srDataLists;
    //注意事项
    private String srNotes;
    //联系人
    private String srContact;
    //电话
    private String srPhone;
    private Integer state;
    private Date stateTime;
    private Date srCreateTime;
    private String srCreateUid;
    private String srCreateUname;
    //抽取人id
    private String extractUid;
    private String extractUname;
    /**
     * 专家选取申请ID
     */
    private String esId;
    //技术类专家总人数（内部）
    private Integer srInsideTechnologySum;
    //经济类专家总人数（内部）
    private Integer srInsideEconomicsSum;
    //技术类人数（内部）
    private Integer srInsideTechnologyNum;
    //经济类人数（内部）
    private Integer srInsideEconomicsNum;
    //专家总人数（内部）
    private Integer srInsideExpertNum;
    //技术类专家总人数（外部）
    private Integer srOutsideTechnologySum;
    //经济类专家总人数（外部）
    private Integer srOutsideEconomicsSum;
    //技术类人数（外部）
    private Integer srOutsideTechnologyNum;
    //经济类人数（外部）
    private Integer srOutsideEconomicsNum;
    //专家总人数（外部）
    private Integer srOutsideExpertNum;
    //技术类专家总人数
    private Integer srTechnologySum;
    //经济类专家总人数
    private Integer srEconomicsSum;
    //技术类人数
    private Integer srTechnologyNum;
    //经济类人数
    private Integer srEconomicsNum;
    //专家总人数
    private Integer srExpertNum;
    //开标时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date srBidTime;
    //开标地点
    private String srLocation;
    //申请单位
    private String srCompany;
    //使用方式
    private String srMode;
    //其他说明
    private String srOtherTxt;

    private String srCompanyName;
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
     * 备注
     */
    private String remark;
}
