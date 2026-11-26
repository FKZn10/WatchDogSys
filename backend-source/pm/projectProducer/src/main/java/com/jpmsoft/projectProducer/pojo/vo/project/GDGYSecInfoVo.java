package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GDGYSecInfoVo {
    private List<SecInfoResponse> data;

    private BigDecimal budgetAmountSum;

    private BigDecimal bidAmountSum;
}
