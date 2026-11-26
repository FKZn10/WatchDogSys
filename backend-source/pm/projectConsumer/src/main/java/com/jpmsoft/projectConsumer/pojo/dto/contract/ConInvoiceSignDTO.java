package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:07
 */
@Data
public class ConInvoiceSignDTO extends BaseFileRequest {

    private static final long serialVersionUID = 7748884473445832108L;

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

    private String isIssuingUnit;
    private String isCollectName;
    @FileFields(index = 1)
    private String isFile;
    private Date isTime;
    private BigDecimal isPaymentAmt;
    private BigDecimal isPaymentRate;
    private BigDecimal isActualAmt;

    @FileFields(index = 1, type = "isFileIds")
    private String isFileIds;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
    private String isIssuingName;

    private List<ConInvoiceInfoDTO> conInvoiceInfos;
}
