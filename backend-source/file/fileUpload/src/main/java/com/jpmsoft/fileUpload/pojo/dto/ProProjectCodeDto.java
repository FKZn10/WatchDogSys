package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 发改委批复项目代码对应项目ID
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/12 11:28
 */
@Data
public class ProProjectCodeDto {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pProjectCode")
    private String pProjectCode;
    @JsonProperty("pCode")
    private String pCode;
}
