package com.jpmsoft.common.model.response.Project;

import lombok.Data;

import java.util.Date;

@Data
public class PreProjectOrgStructUserResponse {

    private String id;
    /**
     * 项目组织机构单位表id
     */
    private String orgStructUnitId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 岗位
     */
    private String orgPost;
    /**
     * 联系方式
     */
    private String orgContact;
    /**
     * 排序
     */
    private Integer orgSort;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateUid;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 组织机构类型
     */
    private String orgStructType;

    private String maintenanceType;

    private String orgStructUnitChangeId;

    private String changeType;

}
