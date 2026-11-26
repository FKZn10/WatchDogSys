package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 开工许可证
 */
@Data
@TableName("Pre_Construction_Permit")
public class PreConstructionPermit {

    @TableId(type = IdType.INPUT)
    private String cpId;
    private String pId;
    private String conId;
    private String cpProjectAddress;
    private BigDecimal cpContractPrice;
    private String cpFixedPrice;
    private Date cpStartTime;
    private Date cpEndTime;
    private String cpOverview;
    private String cpCreateCid;
    private String cpCreateUid;
    private String cpCreateUname;
    private Date cpCreateTime;
    private Integer cpState;
    private Date cpStateTime;

    private String cpProApprDoc;
    private String cpCommApp;
    private String cpHeadquarters;
    private String cpApprovalDoc;
    private String cpConsPermit;
    private String cpQualitySafety;
    private String cpFundProve;
    private String cpHandleProve;
    private String cpContractFile;
    private String cpPerfoPayProve;
    private String cpCompletionProve;
    private String cpPayrollAccount;
    private String cpBillQuantities;
    private Float cpLongitude;
    private Float cpLatitude;
    private String teId;
}
