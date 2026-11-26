package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailDTO;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 询价记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:46
 */
@Data
public class SecInquiryRecordVo extends BaseFileResponse implements Serializable {


    private static final long serialVersionUID = 7431724489708617979L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String secId;
    private Date sirBeginTime;
    private Integer sirPeriod;
    private String sirScope;
    private String sirPrerequisites;
    private BigDecimal sirConMoney;
    private String sirRemark;
    /**
     * 意向单位
     */
    private String sirIntendedUnit;
    private String sirReason;
    private String sirPriceContent;
    private String sirModality;
    private String sirParticipants;
    /**
     * 询价依据
     */
    private String sirInquiryBasis;
    private Date sirEvaluationTime;
    private Date sirWinningBidTime;

    //评标方法
    private String sirFunction;

    //是否接受联合体
    private String sirReceiveJointBidd;

    //资格审查方式
    private String sirQualiReview;

    // 邀请发布日期
    private Date sirInviteDate;

    //计划开标时间
    private Date sirPlanOpenTime;

    //计划开标地点
    private String sirPlanOpenPlace;

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("pBuildModel")
    private String pBuildModel;

    @JsonProperty("pBusinessUnit")
    private String pBusinessUnit;

    @JsonProperty("pCategory")
    private String pCategory;

    private BigDecimal secMoney;
    private String secName;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String secTradingPlace;
    private String tendereeName;
    private String tendereeAbbName;
    private String sirIntendedUnitName;
    private String sirIntendedUnitAbbName;
    private String secAgencyName;
    private List<SecProjectResponse> secProjects;
    private List<SecQuotationUnitVo> secQuotationUnits;


    private List<SecInviteDetailDTO> secInviteDetails;

    private String reserveItems;
}
