package com.jpmsoft.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文档模板扩展配置
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 11:13
 */
@Data
public class SysExtendConfDTO implements Serializable {

    private static final long serialVersionUID = 1213522992469336640L;

    private String id;
    @JsonProperty("tId")
    private String tId;
    private String ecName;
    private String ecRemark;
    private Integer ecSort;
    private Boolean ecDefValue;
    
    @JsonProperty("tCode")
    private String tCode;
    private String ecPrompt;
}
