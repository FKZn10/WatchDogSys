package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单
 */
@Data
public class ConBoqDto extends BaseFileRequest implements Serializable {

    /**
     * AI识别后台任务ID。
     */
    private String runId;


    private static final long serialVersionUID = -6056771200893305333L;
    private String boqId;
    private String boqTitle;
    private String boqRemark;
    private String boqVersion;
    /**
     * 清单版本。
     */
    private String conBoqVersion;
    /**
     * 清单类型。
     */
    private String conBoqType;
    private String boqCreateUid;
    private String boqCreateUname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqCreateTime;
    private Integer boqState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqStateTime;
    @JsonProperty("pId")
    private String pId;
    private String conId;

    private List<ConBoqDtlDto> conBoqDtls;
    @JsonProperty("pName")
    private String pName;
    private String conName;
    /**
     * createOrUpdateConBoq是否接口操作dtl表接口
     */
    private Boolean actionDtl=true;
}
