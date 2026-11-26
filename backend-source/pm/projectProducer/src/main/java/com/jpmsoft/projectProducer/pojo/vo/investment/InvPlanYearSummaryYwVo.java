package com.jpmsoft.projectProducer.pojo.vo.investment;


import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import lombok.Data;

import java.util.List;

@Data
public class InvPlanYearSummaryYwVo {

    private String pName;

    private List<InvPlanYearResponse> years;



}
