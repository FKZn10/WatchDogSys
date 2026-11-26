package com.jpmsoft.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NoMajproWeeklyNewspaperResponse {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pUseUnitName")
    private String pUseUnitName;
    private List<SysUserVo> userList;
}
