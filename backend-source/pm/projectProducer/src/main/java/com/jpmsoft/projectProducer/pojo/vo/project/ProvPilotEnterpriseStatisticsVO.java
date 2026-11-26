package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 试点企业卡片统计，评估、验收没有业务数据来源时返回null并标记不可用。
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ProvPilotEnterpriseStatisticsVO {
    /**
     * 试点企业记录总数，不按名称去重。
     */
    private Long total;
    /**
     * 已中期评估数量；没有数据来源时为null。
     */
    private Long midtermEvaluated;
    /**
     * 已验收数量；没有数据来源时为null。
     */
    private Long accepted;
    /**
     * 中期评估和验收统计是否已有可用业务数据来源。
     */
    private boolean assessmentAvailable = false;
    /**
     * 评估及验收统计不可用的原因。
     */
    private String unavailableReason = "试点企业尚无中期评估、验收数据来源";
}
