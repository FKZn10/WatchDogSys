package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("company_register")
public class CompanyRegister implements Serializable {

    private static final long serialVersionUID = -8942972951123940379L;
    /**
     * id
     */
    private String id;

    /**
     *
     */
    private String creditCode;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String type;
    /**
     * 省市区
     */
    private String provinceCityDistrict;

    /**
     *
     */
    private String address;

    /**
     *
     */
    private String licenseUrl;

    /**
     *
     */
    private String contactPerson;

    /**
     *
     */
    private String contactPhone;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private String auditComment;

    /**
     *
     */
    private String declarationAttachment;

    /**
     * 审批时间
     */
    private LocalDateTime stateTime;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人
     */
    private String createUname;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    private String userId;
    private String cId;
}