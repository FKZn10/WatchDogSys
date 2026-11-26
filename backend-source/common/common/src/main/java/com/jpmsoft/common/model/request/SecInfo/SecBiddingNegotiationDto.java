package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SecBiddingNegotiationDto extends BaseFileRequest {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    @JsonProperty("pId")
    private String pId;

    private String secId;

    private String bnQualiReview;

    private String bnFunctionText;

    private String bnRemark;

    private String bnReceiveJointBidd;

    @JsonProperty("pName")
    private String pName;

    private String secName;
    private String secCode;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;

    //邀请发布日期
    private Date bnInvitePublishDate;
    //计划开标时间
    private Date bnPlanOpenTime;
    //计划开标地点
    private String bnPlanOpenLocation;

    @JsonProperty("pBuildModel")
    private String pBuildModel;

    @JsonProperty("pBusinessUnit")
    private String pBusinessUnit;

    @JsonProperty("pCategory")
    private String pCategory;

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

    private String inAssociatedDrawings;

    private String bnFunctionTextOther;

    private String bnIsEvaluateSeparation;

    private String bnIsTwoStage;

    private String bnCalibrationMethod;

    private String bnFileType;

    private String bnConFile;

    /**
     * 控制价盖章文件
     */
    private String bnControlPriceFile;

    private String bnUrl;

    private List<String> participationOrFinished;
}
