package com.jpmsoft.projectProducer.pojo.dto.peinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/25 18:42
 */
@Data
public class PeMoneyReturnDTO extends BaseFileRequest implements Serializable {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    private BigDecimal mrConAmt;
    private String mrType;
    private String mrContent;
    private BigDecimal mrAmt;
    private String mrReason;
    private String mrConstruction;
    private String mrRemark;


    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
}
