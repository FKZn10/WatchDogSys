package com.jpmsoft.projectConsumer.request.fund;

import lombok.Data;

@Data
public class GroupFundPlanSummaryDetailRequest {
    private String id;
    private String gfpsId;
    private String gfpId;
    //Gfp_Fund_Apply_Amount_Gc_Audit 资金申请工程部审核数
    private Double gfpFundApplyAmountGcAudit;
    //Gfp_Fund_Apply_Amount_Cw_Audit 资金申请财务部审核数
    private Double gfpFundApplyAmountCwAudit;
}
