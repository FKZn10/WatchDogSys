package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.List;

@Data
public class ConContractRequestNew extends BaseFileRequest {
    private String conName;
    private String conCode;
    private List<String> conMoneyTotals;
    private String conType;
    private List<String> conDates;

    private String conApprovalState;
    private String excludeSeven;

    @JsonProperty("pName")
    private String pName;

    private String conCategory;

}
