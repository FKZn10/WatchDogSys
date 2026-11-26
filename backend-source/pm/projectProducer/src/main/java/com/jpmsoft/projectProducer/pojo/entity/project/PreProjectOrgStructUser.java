package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目组织机构的单位人员
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Data
@TableName("pre_project_org_struct_user")
public class PreProjectOrgStructUser implements Serializable {
    private static final long serialVersionUID = 378678724648871167L;
    /**
     * id
     */
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



}

