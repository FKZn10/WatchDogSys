package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailDTO;
import lombok.Data;

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
public class SecInquiryRecordDto extends BaseFileDto {

    private static final long serialVersionUID = -2575094714646566903L;

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
    //报价单位
    private List<SecQuotationUnitDto> secQuotationUnits;

    private List<SecInviteDetailDTO> secInviteDetails;

    private String sirIntendedUnitName;
    private String secName;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private String secPlace;
    private String secTradingPlace;
    private String tendereeName;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pName")
    private String pName;
    private List<String> secStartTimes;
    private List<String> createTimes;
    private List<String> secBiddingCycles;
    private List<String> sirConMoneys;

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


    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "case when pre.P_Name is null then sp.P_Name else pre.P_Name end", no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "标段名称", type = "string", value = "secNameList", dbValue = "sec.sec_Name", no = 2)
    private List<String> secNameList;

    @JsonProperty("pCategoryList")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategoryList", dbValue = "case when pre.P_Category is null then sp.p_Category else pre.P_Category end",dictionary = "JPMsoft_NDTZXMFL", no = 3)
    private List<String> pCategoryList;

    @JsonProperty("createUnameList")
    @ClassInfo(title = "发起人", type = "string", value = "createUnameList", dbValue = "mn.create_Uname", no = 4)
    private List<String> createUnameList;

    @JsonProperty("createTimeStr")
    @ClassInfo(title = "发起时间", type = "date", value = "createTimeStr", dbValue = "mn.create_Time", no = 5)
    private String createTimeStr;


    @JsonProperty("stateList")
    @ClassInfo(title = "流程状态", type = "dictionary", value = "stateList", dbValue = "mn.state",dictionary = "JPMsoft_LCZT", no = 6)
    private List<Integer> stateList;

    private String isNew;

    private String projectId;

    private List<String> participationOrFinished;
}
