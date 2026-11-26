package com.jpmsoft.common.model.request.Construction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 参建单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/10 19:37
 * @TableName Completion_Acceptance_Company
 */
@Data
public class CompletionAcceptanceCompanyDto implements Serializable {


    private static final long serialVersionUID = 6906947697918625411L;
    private String id;
    private String caId;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cType")
    private String cType;
    private String cacPersonLiable;
    private String cacTelephone;

}