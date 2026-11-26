package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.requestAndResponse.Project.PreProProposalModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/18 16:05
 */
@Data
public class PreProProposalStatisticsVo implements Serializable {

    private static final long serialVersionUID = 4528383621635573502L;
    private BigDecimal ppInvestmentSum = BigDecimal.ZERO;
    private List<PreProProposalModel> data;

}
