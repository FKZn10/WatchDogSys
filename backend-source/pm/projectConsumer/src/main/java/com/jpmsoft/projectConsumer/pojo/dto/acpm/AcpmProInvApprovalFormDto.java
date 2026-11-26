package com.jpmsoft.projectConsumer.pojo.dto.acpm;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 项目投拓立项 表单台账查询
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:34
 */
@Data
public class AcpmProInvApprovalFormDto extends AcpmProjectInvestmentApprovalDto implements Serializable {

    private static final long serialVersionUID = -6151071844936915525L;

    private List<String> piaTenderClosingTimes;
    private List<String> piaAnswerClosingTimes;
    private List<String> piaBidBondMoneys;
    private List<String> piaPerformanceBonds;
    private List<String> piaAgencyFees;
    private List<String> piaPeoples;
    private List<String> piaPerCapitas;
    private List<String> piaServiceFees;
    private List<String> piaStampDutys;
    private List<String> piaAchievements;
    private List<String> piaLaborCosts;
    private List<String> piaTotalCosts;
    private List<String> piaGrossProfits;
    private List<String> piaNetProfits;
    private List<String> piaGrossProfitMargins;
    private List<String> piaProvisionalInvestments;
    private List<String> piaGuaranteeAmtReqs;
}
