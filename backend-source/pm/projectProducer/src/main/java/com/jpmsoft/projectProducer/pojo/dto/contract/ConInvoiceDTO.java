package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同收款发票
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/19 8:54
 */
@Data
public class ConInvoiceDTO extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -6881434924130185914L;
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

    private String ciType;
    private String ciCode;
    private String ciStatus;
    private String ciIncomeStatus;
    private String ciPayerId;
    private String ciPayerUnit;
    private BigDecimal ciXxAmt;
    private BigDecimal ciXxTaxAmt;
    private BigDecimal ciKpAmt;
    private BigDecimal ciKpTaxAmt;
    private String ciDxTaxAmt;
    private String ciRemark;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String ciPayerName;
}
