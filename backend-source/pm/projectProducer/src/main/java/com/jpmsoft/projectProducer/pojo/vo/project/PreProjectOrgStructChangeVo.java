package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目组织机构（变更表）
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Data
public class PreProjectOrgStructChangeVo implements Serializable {
    private static final long serialVersionUID = 426058449687738222L;
    /**
     * id
     */
    private String id;
    /**
     * 组织机构表id
     */
    private String orgStructId;
    /**
     * 项目id
     */
    @JsonProperty("pId")
    private String pId;
    /**
     * 证明文件
     */
    private String proofFile;
    /**
     * 备注
     */
    private String remark;
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
     * 流程状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;

    @JsonProperty("pName")
    private String pName;

    private String pCompetentDepartmentName;

    private String pCompetentDepartment;


}

