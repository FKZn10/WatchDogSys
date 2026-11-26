package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 点击合同后展示的已审批或未完成审批变更汇总表，金额单位元。
 */
@Data
public class ContractChangeSummaryVO {
    private String conId;
    private String conName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportTime;
    private List<ChangeRow> details = new ArrayList<>();
    private ChangeRow total;

    /**
     * 一条变更记录；明细金额保留数据库空值，合计按零处理。
     */
    @Data
    public static class ChangeRow {
        private Integer sequence;
        private String ecId;
        /**
         * 变更事项，来源 change_name。
         */
        private String changeName;
        /**
         * 报审金额，来源 party_b_amount，单位元。
         */
        private BigDecimal partyBAmount;
        /**
         * 初审金额，来源 party_a_amount，单位元。
         */
        private BigDecimal partyAAmount;
        /**
         * 两类接口均取 Ec_State_Time，未指定时为空。
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date ecStateTime;
        private String ecRemark;
        private Integer ecState;
    }
}
