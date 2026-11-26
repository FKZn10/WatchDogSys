package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 变更费用申报可关联的现场签证。
 */
@Data
public class ConEcFeeSiteVisaRelationVO {

    private String svisId;
    private String pId;
    private String svisName;
    private String svisProjectName;
    private String svisCode;
    private BigDecimal svisContractorAmt;
    private String svisCreateUid;
    private String svisCreateUname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date svisCreateTime;

    private Integer svisState;
    private String svisProposedId;
    private String svisMajor;
    private String svisContentSummary;
}
