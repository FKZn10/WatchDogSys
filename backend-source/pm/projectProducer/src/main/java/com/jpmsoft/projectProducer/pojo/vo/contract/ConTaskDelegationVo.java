package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/5 14:14
 */
@Data
public class ConTaskDelegationVo extends ProjectCommonRes implements Serializable {


    private static final long serialVersionUID = 9132597471106703583L;

    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String conId;
    private String ctdType;
    private String ctdCode;
    private String ctdProNo;
    private String ctdSuperviseNo;
    private Date ctdDate;
    private String ctdCpy;
    private String ctdContacts;
    private String ctdContactsTel;
    private String ctdSoutCpy;
    private String ctdSoutContacts;
    private String ctdSoutContactsTel;
    private String ctdQualityCpy;
    private String ctdProAddress;
    private String ctdSampler;
    private String ctdWitness;
    private String ctdSamplerTel;
    private String ctdWitnessTel;
    private String ctdProType;
    private Date ctdMeasure;
    private Date ctdDeliverables;
    private String ctdRequire;
    private String ctdContent;
    private String ctdScope;
    private String ctdRemark;
    private String conAnnualId;

    private String conName;
    private String conCode;
    private String conPricingMethod;
    private String conOtherValuation;
    private String conAnnualName;
    private String conAnnualCode;
    private String conAnnualPricingMethod;
    private String conAnnualOtherValuation;
    private BigDecimal conMoneyTotal;
    private String ctdCpyName;
    private String ctdCpyAbbName;
    private String ctdSoutCpyName;
    private String ctdSoutCpyAbbName;
    private String ctdQualityCpyName;
    private String ctdQualityCpyAbbName;
}
