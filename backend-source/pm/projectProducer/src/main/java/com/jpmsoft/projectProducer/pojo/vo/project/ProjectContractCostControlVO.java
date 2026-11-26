package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 施工合同造价控制表上半部分，金额单位元。
 */
@Data
public class ProjectContractCostControlVO {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportTime;
    private List<ContractRow> contractDtls = new ArrayList<>();
    /**
     * 金额列合计；变更比例按合计金额重新计算。
     */
    private ContractRow total;

    /**
     * 一份已审批合同对应一行；暂列金已包含在合同总价中，不重复计入合计。
     */
    @Data
    public static class ContractRow {
        private Integer sequence;
        private String conId;
        /**
         * C列合同名称；合计行固定为“合计”。
         */
        private String conName;
        /**
         * D列合同总价。
         */
        private BigDecimal conMoneyTotal = BigDecimal.ZERO;
        /**
         * E列其中暂列金。
         */
        private BigDecimal conMoneyTentative = BigDecimal.ZERO;
        /**
         * F列施工图预算，来源待定，返回 null。
         */
        private BigDecimal constructionDrawingBudget;
        /**
         * G列：Ec_State=7 的 party_a_amount 合计。
         */
        private BigDecimal approvedChangeAmount = BigDecimal.ZERO;
        /**
         * H列：Ec_State NOT IN (7,3,0) 的 party_a_amount 合计，NULL 状态不计入。
         */
        private BigDecimal pendingChangeAmount = BigDecimal.ZERO;
        /**
         * I列：G+H。
         */
        private BigDecimal changeSubtotal = BigDecimal.ZERO;
        /**
         * J列：待增项预估，暂无来源，返回 null。
         */
        private BigDecimal additionalEstimate;
        /**
         * K列：竣工结算时调差预估等，暂无来源，返回 null。
         */
        private BigDecimal settlementAdjustmentEstimate;
        /**
         * L列：其他增价预估小计，暂无来源，返回 null。
         */
        private BigDecimal otherIncreaseSubtotal;
        /**
         * M列：D+I；其他增价预估暂无数据，不增加金额。
         */
        private BigDecimal totalAmount = BigDecimal.ZERO;
        /**
         * N列：I/D，返回小数比例（0.1 表示10%），保留6位；合同总价为0时为null。
         */
        private BigDecimal changeRatio;
    }
}
