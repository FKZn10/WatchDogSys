package com.jpmsoft.projectProducer.pojo.dto.materialsEquipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 认质认价
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:45
 */
@Data
public class MeQualityPriceDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -7391053674748165246L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String qpId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String qpFirstPartyRid;
    private String qpFirstPartyRname;
    private String qpContractorId;
    private String qpSupervisorId;
    private String qpInitiator;
    private String qpInitiatorName;
    private Date qpInitiationTime;
    private String qpTitle;
    private String qpReason;
    private String qpRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String qpCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String qpCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date qpCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private String qpState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date qpStateTime;


    @JsonProperty("pName")
    private String pName;
    private String conName;
    private List<MeQualityPriceDtlDto> meQualityPriceDtls;

}
