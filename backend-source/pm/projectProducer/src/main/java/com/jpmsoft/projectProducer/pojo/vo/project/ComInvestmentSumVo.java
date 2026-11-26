package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/18 15:58
 */
@Data
public class ComInvestmentSumVo implements Serializable {

    private static final long serialVersionUID = 3918193481689636167L;
    private BigDecimal comInvestmentSum = BigDecimal.ZERO;
    private List<PreCommandPostModel> data;

}
