package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目组织机构单位表
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Data
@TableName("pre_project_org_struct_unit")
public class PreProjectOrgStructUnit implements Serializable {
    private static final long serialVersionUID = -49944335614794464L;
    /**
     * id
     */
    private String id;
    /**
     * 项目组织机构表id
     */
    private String orgStructId;
    /**
     * 单位所属类型 01:立项相关单位；02：五方责任主体；03、其他单位
     */
    private String unitType;
    /**
     * 组织机构类型
     */
    private String orgStructType;
    /**
     * 是否有XX单位
     */
    private Integer haveUnit;
    /**
     * 单位id
     */
    private String unitId;

    //排序
    private Integer sort;

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

