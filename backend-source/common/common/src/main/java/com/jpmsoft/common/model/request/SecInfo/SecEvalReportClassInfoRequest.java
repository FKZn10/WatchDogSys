package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.List;

/**
 * 评标报告
 */
@Data
public class SecEvalReportClassInfoRequest extends BaseFileRequest {
    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "case when pre.P_Name is null then sp.P_Name else pre.P_Name end", no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "标段名称", type = "string", value = "secNameList", dbValue = "sec.sec_Name", no = 2)
    private List<String> secNameList;

    @JsonProperty("pCategoryList")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategoryList", dbValue = "case when pre.P_Category is null then sp.p_Category else pre.P_Category end", dictionary = "JPMsoft_NDTZXMFL", no = 3)
    private List<String> pCategoryList;

    @JsonProperty("createUnameList")
    @ClassInfo(title = "发起人", type = "string", value = "createUnameList", dbValue = "mn.Er_Create_Uname", no = 4)
    private List<String> createUnameList;

    @JsonProperty("createTimeStr")
    @ClassInfo(title = "发起时间", type = "date", value = "createTimeStr", dbValue = "mn.Er_Create_Time", no = 5)
    private String createTimeStr;

    @JsonProperty("stateList")
    @ClassInfo(title = "流程状态", type = "dictionary", value = "stateList", dbValue = "mn.Er_State", dictionary = "JPMsoft_LCZT", no = 6)
    private List<Integer> stateList;

    private List<String> participationOrFinished;

    private String isNew;

    private String secName;

    private String secId;
}
