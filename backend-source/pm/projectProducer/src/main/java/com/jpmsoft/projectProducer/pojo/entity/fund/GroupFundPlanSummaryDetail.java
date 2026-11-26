package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Group_Fund_Plan_Summary_Detail")
public class GroupFundPlanSummaryDetail {
    private String id;
    private String gfpsId;
    private String gfpsSummaryId;
    private String gfpId;
    // Gfps_Project_Competent_Department_Id
    private String gfpsProjectCompetentDepartmentId;
    // Gfp_Fund_Apply_Amount_Gc_Audit 资金申请工程部审核数
    private BigDecimal gfpFundApplyAmountGcAudit;
    // Gfp_Fund_Apply_Amount_Cw_Audit 资金申请财务部审核数
    private BigDecimal gfpFundApplyAmountCwAudit;
    // status
    private Integer status;
    // del_flag
    private Integer delFlag;
    private String remarkContext;
}
