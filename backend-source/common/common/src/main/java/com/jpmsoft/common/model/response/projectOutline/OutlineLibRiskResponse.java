package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板风险指标
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Data
public class OutlineLibRiskResponse {
    private String id;
    /**
     * 体系模板-目录节点ID
     */
    private String oslId;
    private String riskId;
    private Integer olrSort;
    private Date createTime;
    private String createUid;
    private String createUname;

    // 下面是risk_warn_index
    private String rwiCode;
    private String rwiType;
    private String rwiName;
    private String rwiDesc;
    private String rwiProblem;
    private String rwiCalculationRule;
    private String rwiValue;
    private String rwiBasis;
    private String rwiLevel;
    private String rwiCreateUid;
    private String rwiCreateUname;
    private Date rwiCreateTime;
    private Integer rwiState;
    @JsonProperty("dId")
    private String dId;
    private String rwiPersonUid;
    private String rwiPersonUname;
    private Integer rwiProjectLeader;
    private String rwiBusinessInforUrl;
    private String rwiScreeningConditions;
    private String rwiQuerySql;
    private String rwiCcPost;
    private String rwiCcPostFilter;
    private String rwiCcSmsType;
    private String rwiCcSmsTemplate;
    private String teId;
}