package com.jpmsoft.projectProducer.pojo.vo.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单
 */
@Data
public class ConBoqBimPageVO implements Serializable {


    private static final long serialVersionUID = -1619366521414819206L;

    private String boqId;
    private String boqTitle;
    private String boqRemark;
    private String boqVersion;
    private String boqCreateUid;
    private String boqCreateUname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqCreateTime;
    private Integer boqState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqStateTime;
    private String conId;

    private String conName;
}
