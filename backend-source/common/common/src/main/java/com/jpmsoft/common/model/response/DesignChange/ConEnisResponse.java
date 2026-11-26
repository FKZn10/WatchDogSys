package com.jpmsoft.common.model.response.DesignChange;


import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程洽商指令单
 */
@Data
public class ConEnisResponse extends ProjectCommonRes {

    private String enisId;
    private String conId;
    private String enisCode;
    private String enisProjectName;
    private String enisProposedId;
    private String enisMajor;
    private String enisDrawingNo;
    private Date enisDate;
    private String enisContentSummary;
    private String enisContent;
    private String enisCreateUid;
    private String enisCreateUname;
    private Date enisCreateTime;
    private Integer enisState;
    private Date enisStateTime;
    private String enisType;
    private String enisLocation;
    private String enisDep;
    private Date enisFinishDate;
    private String enisName;
    private String enisApprovalMatter;
    private String enisCategory;
    private BigDecimal enisContractorAmt;
    private String enisMainCode;
    private String enisSubCode;
    private BigDecimal enisHbMoney;
    private String enisHbRemark;
    private String enisHbCheck;

    private String enisDepName;
    private String conName;
    private String enisProposedName;
    private String enisProposedNameHistory;
    //单位简称
    private String enisProposedAbbName;
    private BigDecimal enbEstimateAmt;
    /**
     * 监理预算
     */
    private BigDecimal enisSupervisionAmt;
}
