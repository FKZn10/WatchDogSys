package com.jpmsoft.projectProducer.pojo.dto.investment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 概算追加
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:12
 */
@Data
public class InvBudgetDeclareAddDTO extends BaseFileRequest {
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
    private BigDecimal bdaMoney;
    private String bdaRemark;
    private BigDecimal bdaProBudget;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
}
