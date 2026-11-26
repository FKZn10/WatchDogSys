package com.jpmsoft.common.model.response.Construction;

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
public class CompletionAcceptanceCompanyVo implements Serializable {

    private static final long serialVersionUID = -9144118846064288048L;

    private String id;
    private String caId;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;
    @JsonProperty("cType")
    private String cType;
    private String cacPersonLiable;
    private String cacTelephone;

}