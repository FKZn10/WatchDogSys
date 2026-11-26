package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:49
 */
@Data
public class SecQuotationUnitDto implements Serializable {

    private static final long serialVersionUID = 8147825240331053386L;

    private String id;
    private String sirId;
    @JsonProperty("cId")
    private String cId;
    private BigDecimal squAmt;
    private Date squTime;
    //资质等级
    private String squQualificationLevel;
    //联系人
    private String squContacts;
    //联系电话
    private String squContactNumber;
    private String squRemark;
    private Integer squSort;
    private String squFile;
    private String squFileIds;
}
