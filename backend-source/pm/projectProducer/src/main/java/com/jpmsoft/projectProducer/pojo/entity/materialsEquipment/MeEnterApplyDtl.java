package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 材料设备进场报审清单
 */
@Data
@TableName("Me_Enter_Apply_Dtl")
public class MeEnterApplyDtl {

    @TableId(type = IdType.INPUT)
    private String eadId;
    private String metId;
    private String eaId;
    private String eadStandardType;
    private Float eadCount;
    private String eadBrandId;
    private String eadBrandName;
    private String eadManufacturer;
    private String eadSupplierId;
    private String eadSupplierName;
    private String eadSupplyOrderNo;
    private String eadBatchNumber;
    private Date eadSupplyOrderNoDate;
    private Date eadProductDate;
    private String eadProductionAddress;
    private String eadRemark;
    private Date eadCreateTime;
    private String eadUsedPart;
    private String eadAcceptorId;
    private String eadAcceptorName;
    private String eadAcceptAp;
    private Float eadAcceptCount;
    private Integer eadBrandOk;
    private Integer eadCountOk;
    private Integer eadStandardTypeOk;
    private Date eadAcceptTime;
    private String eadAcceptRemark;
    private String eadSupplierCode;
    private String eadSupplierType;

    private String eadUnit;
}
