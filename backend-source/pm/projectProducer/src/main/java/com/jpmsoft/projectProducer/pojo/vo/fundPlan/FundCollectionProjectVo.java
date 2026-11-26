package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/27 14:53
 */
@Data
public class FundCollectionProjectVo extends PreProjectResponse {

    private static final long serialVersionUID = 8959339382946812559L;

    private BigDecimal fcdMoneySum;


}
