package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标工作方案
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 9:30
 */
@Data
public class SecWorkSchemaDTO extends BaseFileRequest implements Serializable {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private String secId;

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;
    private String wsRemark;
    //询价文件
    @FileFields(index = 1)
    private String inquiryDoc;
    @FileFields(index = 1, type = "inquiryDocFileIds")
    private String inquiryDocFileIds;


    private String secName;
    private String secOrganizeForm;
    private String secBiddingType;
    private String tendereeName;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String secType;
    private BigDecimal secMoney;
    private String secAgencyName;
    private String secContractingDepartment;
    private String secTradingPlace;
    private String secOtherValuation;
    private String secPricingMethod;

    private List<SecInviteDetailDTO> secInviteDetails;
    private List<SecProjectResponse> secProjects;


    @JsonProperty("pCategory")
    private String pCategory;


    private String secSectionClassify;

    @JsonProperty("pBuildModel")
    private String pBuildModel;

    @JsonProperty("pBusinessUnit")
    private String pBusinessUnit;


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

    private String projectId;

    private String isNew;
    private List<String> participationOrFinished;
}
