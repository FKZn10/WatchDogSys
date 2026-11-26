package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.Date;

/**
 * 任务委托单
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/5 13:54
 */
@Data
public class ConTaskDelegationDto extends BaseFileDto {

    private static final long serialVersionUID = 400262265593506477L;

    @JsonProperty("pId")
    private String pId;
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

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String conAnnualName;

    private String noAuth;
}
