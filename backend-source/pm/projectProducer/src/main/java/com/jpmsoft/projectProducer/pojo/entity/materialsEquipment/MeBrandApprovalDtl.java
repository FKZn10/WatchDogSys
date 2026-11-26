package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 品牌报审清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:20
 */
@Data
@TableName("Me_Brand_Approval_Dtl")
public class MeBrandApprovalDtl {

    @TableId(type = IdType.INPUT)
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
