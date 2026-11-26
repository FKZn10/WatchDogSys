package com.jpmsoft.projectProducer.pojo.dto.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单
 */
@Data
public class ConBoqBimPageDto{

    private String boqId;
    private String boqTitle;
    private String boqRemark;
    private String boqVersion;
    private String boqCreateUid;
    private String boqCreateUname;
    private Date boqCreateTime;
    private Integer boqState;
    private Date boqStateTime;
    private String conId;

    private String conName;
}
