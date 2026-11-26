package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单
 */
@Data
public class ConBoqVo extends ProjectCommonRes implements Serializable {

    /**
     * AI识别后台任务ID。
     */
    private String runId;


    private static final long serialVersionUID = -1619325521414819206L;

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
    private String conId;

    private List<ConBoqDtlVo> conBoqDtls;
    private String conName;
}
