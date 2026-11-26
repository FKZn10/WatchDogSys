package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理大纲体系管理风险指标
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Data
public class OutlineProjectRiskResponse {
    private String id;
    private String riskId;
    private Integer oprSort;
    /**
     * 项目管理大纲体系目录节点id
     */
    private String oplId;
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

    private String opmId;
    private Integer rwState;
}
