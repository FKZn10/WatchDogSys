package com.jpmsoft.projectProducer.pojo.vo.project;

import lombok.Data;

/**
 * 项目登记、申报和论证卡片的状态统计。
 */
@Data
public class ProjectRegisterStatisticsVO {
    /**
     * 总数，包含草稿、停止及未知状态。
     */
    private Long total;
    /**
     * 审核或论证中，状态非空且不属于0、3、7。
     */
    private Long approving;
    /**
     * 已审核或流程结束，状态为7。
     */
    private Long approved;
}
