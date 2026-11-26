package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保函签收
 */
@Data
@TableName("Guarantee_Letter_Sign")
public class GuaranteeLetterSign {
    @TableId(type = IdType.INPUT)
    private String glsId;
    private String pId;
    private String secId;
    private String conId;
    private String glsCode;
    private String glsType;
    private String glsGuarantor;
    private BigDecimal glsMoney;
    private String glsWarrantee;
    private String glsWarranteeTel;
    private Date glsIssuanceDate;
    private Integer glsNoTimeLimit;
    private Date glsDueDate;
    private String glsCreateUid;
    private String glsCreateUname;
    private Date glsCreateTime;
    private Integer glsState;
    private Date glsStateTime;
    private String glsBeneficiary;
    private String glsNum;
    private String glsWarranteePerson;
    private String glsFillingDepartment;
    private Integer glsIsrenewal;
    private String glsRenewalId;
    private String glsDueDateRemark;
    private String glsIssuanceDateRemark;
    private String teId;

    private Date glsSignDate;

    private String glsJlxs;
}
