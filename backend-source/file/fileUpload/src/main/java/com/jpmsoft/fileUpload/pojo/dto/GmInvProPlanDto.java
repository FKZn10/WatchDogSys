package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 光明 资金计划下达导入
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/12 10:57
 */
@Data
public class GmInvProPlanDto implements Serializable {

    private static final long serialVersionUID = 125709213792180508L;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String ippBudgetItemName;
    private String ippNdrcCode;
    private String ippYear;
    private String ippDocNumber;
    private String ippPlanDocNo;
    private String ippSourceFunds;
    private String ippReleaseType;
    private String ippReleaseFunds;
    private String ippRemarks;

}
