package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 项目单位报备
 *
 * @TableName Reported_Company
 */
@Data
@TableName("Reported_Company")
public class ReportedCompany extends BaseEntity {

    /**
     * 项目Id
     */
    private String pId;

    /**
     * 单位名称
     */
    private String cName;

    /**
     * 单位简称
     */
    private String cAbbreviation;

    /**
     * 单位编码
     */
    private String cCode;

    /**
     * 单位类型
     */
    private String cType;

    /**
     * 统一社会信用代码
     */
    private String cCreditcode;

    /**
     * 单位法人
     */
    private String cUnitlegalperson;

    /**
     * 单位联系人
     */
    private String cContactperson;

    /**
     * 单位联系人电话
     */
    private String cContactpersontel;

    /**
     * 单位网址
     */
    private String cWebsite;

    /**
     * 备注
     */
    private String cRemark;

    /**
     * 客户编码
     */
    private String cSolution;

    /**
     * 营业执照
     */
    private String cBusinessLicense;

    /**
     * 资质证明
     */
    private String cQualification;

    /**
     * 其他文件
     */
    private String cCredentials;
    private String teId;
}