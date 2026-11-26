package com.jpmsoft.projectProducer.pojo.dto.oa;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目单位报备
 *
 * @TableName Reported_Company
 */
@Data
public class ReportedCompanyDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = -9211040653981249018L;
    /**
     * 项目Id
     */
    @JsonProperty("pId")
    private String pId;

    /**
     * 单位名称
     */
    @JsonProperty("cName")
    private String cName;

    /**
     * 单位简称
     */
    @JsonProperty("cAbbreviation")
    private String cAbbreviation;

    /**
     * 单位编码
     */
    @JsonProperty("cCode")
    private String cCode;

    /**
     * 单位类型
     */
    @JsonProperty("cType")
    private String cType;

    /**
     * 统一社会信用代码
     */
    @JsonProperty("cCreditcode")
    private String cCreditcode;

    /**
     * 单位法人
     */
    @JsonProperty("cUnitlegalperson")
    private String cUnitlegalperson;

    /**
     * 单位联系人
     */
    @JsonProperty("cContactperson")
    private String cContactperson;

    /**
     * 单位联系人电话
     */
    @JsonProperty("cContactpersontel")
    private String cContactpersontel;

    /**
     * 单位网址
     */
    @JsonProperty("cWebsite")
    private String cWebsite;

    /**
     * 备注
     */
    @JsonProperty("cRemark")
    private String cRemark;

    /**
     * 客户编码
     */
    @JsonProperty("cSolution")
    private String cSolution;

    /**
     * 营业执照
     */
    @JsonProperty("cBusinessLicense")
    private String cBusinessLicense;

    /**
     * 资质证明
     */
    @JsonProperty("cQualification")
    private String cQualification;

    /**
     * 其他文件
     */
    @JsonProperty("cCredentials")
    private String cCredentials;


    /**
     * 营业执照
     */
    @JsonProperty("cBusinessLicenseIds")
    private String cBusinessLicenseIds;

    /**
     * 资质证明
     */
    @JsonProperty("cQualificationIds")
    private String cQualificationIds;

    /**
     * 其他文件
     */
    @JsonProperty("cCredentialsIds")
    private String cCredentialsIds;
    /**
     * 项目名
     */
    @JsonProperty("pName")
    private String pName;
    /**
     * 项目编码
     */
    @JsonProperty("pCode")
    private String pCode;
}