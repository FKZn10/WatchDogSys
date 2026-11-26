package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 专家抽取（专家库、建管专家库等）
 *
 */
@Data
public class SecExpertExtractMultipleVo extends SecExpertSelectVo {

    private static final long serialVersionUID = 6938747196943620103L;
    private String id;
    //专家库类型：1、专家库 2、建管专家库
    private Integer expertDbType;
    //对应库的选取记录表id
    private String srId;
    //项目id
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    //标段id
    private String secId;
    //标段名称
    private String secName;
    //招标人Id
    private String secTenderee;
    //代理单位ID
    private String secAgency;
    //招标人(单位)
    private String secTendereeName;
    //代理单位
    private String secAgencyName;
    //招标方式
    private String secBiddingType;
    //组织形式
    private String secOrganizeForm;
    private String secType;

    private Integer state;
    private Date stateTime;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String teId;
    private String currentUid;
    /**
     * 备注
     */
    private String srmRemark;

    //专家库
    private SecExpertExtractVo secExpertExtractVo;

    //建管专家库
    private SecJGExpertExtractVo secJGExpertExtractVo;

}
