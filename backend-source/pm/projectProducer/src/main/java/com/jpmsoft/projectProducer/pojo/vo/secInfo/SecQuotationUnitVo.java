package com.jpmsoft.projectProducer.pojo.vo.secInfo;

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
public class SecQuotationUnitVo implements Serializable {


    private static final long serialVersionUID = -5509817314948173729L;
    private String id;
    private String sirId;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;
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

}
