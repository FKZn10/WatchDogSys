package com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment;

import lombok.Data;

import java.io.Serializable;

/**
 * 品牌报审清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:20
 */
@Data
public class MeBrandApprovalDtlDto  implements Serializable {

    private static final long serialVersionUID = -2230748919212503024L;
    private String badId;
    private String baId;
    private String metId;
    private String badBiddingBrandId;
    private String badBiddingBrand;
    private String badType;
    private String badBrandId;
    private String badBrandName;
    private String badSupplier;
    private Float badCount;
    private String badUnit;
    private String badStandardType;
    private String badPerson;
    private String badTel;
    private String badPersonIdNo;
    private String badUseLocation;
    private String badProducer;
    private String badPlaceOrigin;
    private Integer badSort;
}
