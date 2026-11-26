package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.response.SecInfo.SecBiddingPlanResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/8 16:49
 */
@Data
public class SecBiddingPlanStatisticsVO implements Serializable {


    private static final long serialVersionUID = 7924972383856317180L;

    private int secInfoSum;
    private int finishSum;
    private BigDecimal secMoneySum;

    private List<SecBiddingPlanResponse> data;
}
