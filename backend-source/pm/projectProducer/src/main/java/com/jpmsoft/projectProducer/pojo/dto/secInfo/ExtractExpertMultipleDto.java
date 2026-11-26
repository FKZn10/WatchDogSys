package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 抽取专家（建管）
 *
 */
@Data
public class ExtractExpertMultipleDto implements Serializable {

    private static final long serialVersionUID = -3171669600101179527L;

    //项目id
    private String pId;

    //标段id
    private String secId;


    //专家库类型：1、专家库 2、建管专家库
    private Integer expertDbType;

    //专家库抽取
    private ExtractExpertDto extractExpertDto;

    //建管专家库抽取
    private ExtractJGExpertDto extractJGExpertDto;


}
