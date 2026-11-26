package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 月完成情况
 */
@Data
public class InvCompleteMonthCountForAllResponse implements Serializable {

    private static final long serialVersionUID = 4398987753048002441L;

    /**
     * 综合得分及排名
     */
    private List<InvCompleteMonthScoreResponse> scores;

    /**
     * 开工情况
     */
    private List<InvCompleteMonthCountStartResponse> starts;

    /**
     * 投资情况
     */
    private List<InvCompleteMonthCountInvResponse> investments;

    /**
     * 完工情况
     */
    private List<InvCompleteMonthCountEndResponse> ends;



}
