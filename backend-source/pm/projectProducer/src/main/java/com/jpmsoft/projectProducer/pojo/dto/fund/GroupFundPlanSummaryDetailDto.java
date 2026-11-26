package com.jpmsoft.projectProducer.pojo.dto.fund;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class GroupFundPlanSummaryDetailDto extends BasePageEntity {
    private String id;
    private String gfpsId;
    private String gfpsSummaryId;
    private String gfpId;
    private String gfpsProjectCompetentDepartmentId;
    // Gfp_Fund_Apply_Amount_Gc_Audit 资金申请工程部审核数
    private BigDecimal gfpFundApplyAmountGcAudit;
    // Gfp_Fund_Apply_Amount_Cw_Audit 资金申请财务部审核数
    private BigDecimal gfpFundApplyAmountCwAudit;
    private Integer status;
    private Integer delFlag;
    private String remarkContext;
    @JsonProperty("pId")
    private String pId;
    /**
     * 类型
     */
    private String groupType;

    /**
     * 类型
     */
    private Integer gfpsType;
}
