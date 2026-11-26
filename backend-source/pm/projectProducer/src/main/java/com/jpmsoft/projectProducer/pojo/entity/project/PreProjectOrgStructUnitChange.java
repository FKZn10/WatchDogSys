package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目组织机构单位变更表(PreProjectOrgStructUnitChange)实体类
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Data
@TableName("pre_project_org_struct_unit_change")
public class PreProjectOrgStructUnitChange implements Serializable {
    private static final long serialVersionUID = -76327564866034487L;
    /**
     * id
     */
    private String id;
    /**
     * 项目组织机构变更表id
     */
    private String orgStructChangeId;
    /**
     * 单位所属类型
     */
    private String unitType;
    /**
     * 组织机构类型
     */
    private String orgStructType;
    /**
     * 是否有XX单位
     */
    private Object haveUnit;
    /**
     * 单位id
     */
    private String unitId;
    /**
     * 变更类型
     */
    private String changeType;
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

}

