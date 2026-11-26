package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/18 16:02
 */
@Data
public class PreConstructionPermitStatisticsVo implements Serializable {

    private static final long serialVersionUID = 2585706808522903186L;
    private BigDecimal cpContractPriceSum = BigDecimal.ZERO;
    private List<PreConstructionPermitModel> data;

}
