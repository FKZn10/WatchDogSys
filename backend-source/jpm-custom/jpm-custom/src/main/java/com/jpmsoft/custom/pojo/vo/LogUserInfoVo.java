package com.jpmsoft.custom.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录信息-用户信息
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/15 8:57
 */
@Data
@Accessors(chain = true)
public class LogUserInfoVo implements Serializable {

    private static final long serialVersionUID = -2158915692715142488L;

    private String id;
    private String name;
    private String code;
    private Map<String, Object> position = new HashMap<>();
    @JsonProperty("uType")
    private String uType;
    @JsonProperty("uFirstLogin")
    private Integer uFirstLogin;
    @JsonProperty("uPushId")
    private String uPushId;

    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    private Boolean constructionControl;
    private Boolean constructionUnit;
    private Object departments;
    private List<String> conTables;

    private String teId;
    private boolean enableTenant;
}
