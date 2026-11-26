package com.jpmsoft.projectProducer.pojo.vo.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
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
    /**
     * 用户所在部门ID集合
     * 用户ID能查询到的部门ID 不包含部门的下级或者上级
     */
    private List<String> departmentIds;
    /**
     * 部门授权类型为 项目组织机构授权，负责人部门授权
     * 为主负责人或负责人的部门ID
     */
    private List<String> dutyDepartmentIds;
    private List<String> roleNames;

    private String teId;
    private boolean enableTenant;
    private String loginWay;
    //租户到期时间
    private Date trDate;
    //租户最大项目数量
    private Integer trMaxProject;
    //租户最大用户数
    private Integer trMaxUser;
}
