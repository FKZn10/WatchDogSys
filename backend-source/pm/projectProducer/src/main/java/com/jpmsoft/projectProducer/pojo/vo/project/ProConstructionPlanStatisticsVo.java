package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/27 10:44
 */
@Data
public class ProConstructionPlanStatisticsVo implements Serializable {


    private static final long serialVersionUID = -8018405350460015039L;
    /**
     * 清单计划总投资累计统计
     */
    private BigDecimal cplPlannedInvSum = BigDecimal.ZERO;

    private List<ProConstructionPlanModel> data;
}
