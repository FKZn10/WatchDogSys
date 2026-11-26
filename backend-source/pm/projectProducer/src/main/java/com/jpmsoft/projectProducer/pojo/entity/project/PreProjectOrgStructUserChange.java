package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目组织机构的单位人员变更(PreProjectOrgStructUserChange)实体类
 *
 * @author c
 * @since 2024-12-11 17:39:12
 */
@Data
@TableName("pre_project_org_struct_user_change")
public class PreProjectOrgStructUserChange implements Serializable {
    private static final long serialVersionUID = -26753932664768490L;
    /**
     * id
     */
    private String id;
    /**
     * 项目组织机构单位变更表id
     */
    private String orgStructUnitChangeId;
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
     * 变更类型
     */
    private String changeType;
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

    private String orgStructType;

    private String maintenanceType;


}

