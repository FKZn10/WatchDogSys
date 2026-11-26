package com.jpmsoft.common.model.request.challengePlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChallengePlanAnnualRequest extends BaseFileRequest {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String declareYear;
    @JsonProperty("pId")
    private String pId;
    private String orderNumber;
    private String problems;
    private String planProgress;
    private String remark;
    private String actualBuilderYear;

    @JsonProperty("pBuildYear")
    private String pBuildYear;
}
