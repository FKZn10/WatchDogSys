package com.jpmsoft.common.model.requestAndResponse;

import lombok.Data;

@Data
public class UserDepartmentPDto {
    private String projectId;
    private String uId;
    //职位名称
    private String ppName;
    //角色名称
    private String roleCode;
    //角色名称
    private String roleName;
}
