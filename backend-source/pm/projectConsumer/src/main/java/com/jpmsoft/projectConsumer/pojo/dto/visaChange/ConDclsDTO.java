package com.jpmsoft.projectConsumer.pojo.dto.visaChange;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计变更记录单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 8:34
 */
@Data
public class ConDclsDTO extends BaseFileRequest implements Serializable {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String dclsId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String dclsCode;
    private String dclsProposedUnit;
    private Date dclsTime;
    private String dclsName;
    private String dclsMajor;
    private String dclsChangeReason;
    private String dclsPreChange;
    private String dclsModifiedScheme;
    private BigDecimal dclsPreTotal;
    private BigDecimal dclsModifiedTotal;
    private BigDecimal dclsFirstTotal;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    private String dcoId;
    private String dcbId;

    @JsonProperty("pName")
    private String pName;
    private String conName;


}
