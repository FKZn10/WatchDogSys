package com.jpmsoft.projectProducer.pojo.vo.other;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 框架协议单位查询结果VO
 */
@Data
public class MHCompanyResultVO {

    /**
     * 批次
     */
    private String batchCode;

    /**
     * 单位类型
     */
    private String partyType;

    /**
     * 累计合同暂估价
     */
    private BigDecimal conEstimatedAmountTotal;

    /**
     * 剩余合同暂估价
     */
    private BigDecimal remainingContractMoney;

    /**
     * 单位列表
     */
    private List<CompanyItem> companies;

    /**
     * 单位详情
     */
    @Data
    public static class CompanyItem {
        /**
         * 单位ID
         */
        private String companyId;

        /**
         * 单位名称
         */
        private String companyName;

        /**
         * 单位使用合同暂估价
         */
        private BigDecimal usedContractMoney;
    }
}