package com.jpmsoft.common.model.request.challengePlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChallengePlanMonthlyPageRequest extends BaseFileRequest {
    @JsonProperty("pName")
    @ClassInfo(title = "项目名称", type = "string", value = "pName", dbValue = "p.P_Name")
    private List<String> pName;

    @JsonProperty("pCompetentDepartmentNames")
    @ClassInfo(title = "责任单位", type = "string", value = "pCompetentDepartmentNames", dbValue = "d.D_Name")
    private List<String> pCompetentDepartmentNames;

    @JsonProperty("pBuildYear")
    @ClassInfo(title = "建设年限", type = "string", value = "pBuildYear", dbValue = "p.P_Build_Year")
    private List<String> pBuildYear;

    @ClassInfo(title = "发起人", type = "string", value = "createUname", dbValue = "mn.Create_Uname")
    private List<String> createUname;

    @ClassInfo(title = "执行状态", type = "dictionary", value = "executionStatus", dbValue = "mn.execution_status", dictionary = "JPMsoft_YDJHZXZT")
    private List<String> executionStatus;

    @ClassInfo(title = "流程状态", type = "dictionary", value = "state", dbValue = "mn.State", dictionary = "JPMsoft_YDJHLCZT")
    private List<String> state;

    private Integer orderNumber;
    private String id;
    private String createUid;
    private Date createTime;
    private Date stateTime;
    private String cpaId;
    private String declareYear;
    private String declareMonth;
    private String specificProgress;
}
