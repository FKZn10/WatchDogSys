package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目资金执行明细，金额与面积均保留源字段单位，不进行换算。
 */
@Data
public class ProjectFundExecutionVO {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    /**
     * 报表生成时间，使用 Asia/Shanghai 时区，格式 yyyy-MM-dd HH:mm:ss。
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportTime;
    /**
     * 项目立项投资估算。
     */
    @JsonProperty("pCalculateRoughlyAmount")
    private BigDecimal pCalculateRoughlyAmount;
    /**
     * 项目批复总面积。
     */
    @JsonProperty("pConstructionArea")
    private Float pConstructionArea;
    /**
     * Excel H2 可研单方造价，单位元/㎡。
     * 立项投资估算（元）除以批复总面积（㎡），四舍五入保留两位小数；
     * 缺少投资估算或面积为空、非有限值、不大于零时返回 null。
     */
    private BigDecimal feasibilityUnitCost;
    /**
     * 三类概算科目关联的合同金额合计，不包含未关联当前概算的合同。
     */
    private BigDecimal conMoneyTotal = BigDecimal.ZERO;
    /**
     * Excel F列招标控制价：按标段取最新招标文件后去重汇总。
     */
    private BigDecimal tenderControlPrice = BigDecimal.ZERO;
    /**
     * Excel S列“按合同值或估算值”，按确认口径取合同签订价合计。
     */
    private BigDecimal contractOrEstimateAmount = BigDecimal.ZERO;
    /**
     * 项目自筹小计及年度明细。
     */
    private List<PaidAmount> selfPaidDtls = new ArrayList<>();
    /**
     * 项目国拨小计及年度明细。
     */
    private List<PaidAmount> governmentPaidDtls = new ArrayList<>();
    /**
     * 固定为工程费用、工程建设其他费、基本预备费，下挂概算科目树。
     */
    private List<ContractDetail> contractDtls = new ArrayList<>();

    /**
     * 一行概算科目；合同金额、自筹及国拨金额包含本级和全部下级的直接发生额。
     */
    @Data
    public static class ContractDetail {
        /**
         * 概算明细主键 Bdd_Id，固定分类无对应数据时为空。
         */
        private String bddId;
        private String bddParentId;
        private String bddNumber;
        private String bddCode;
        private String bddName;
        /**
         * 固定分类编码，仅外层三类有值：ENGINEERING、OTHER、RESERVE。
         */
        private String category;
        /**
         * 概算原始投资金额，不与下级重复相加。
         */
        private BigDecimal bddTotal;
        /**
         * 本级与下级关联合同签订价之和。
         */
        private BigDecimal conMoneyTotal = BigDecimal.ZERO;
        /**
         * Excel F列招标控制价，包含本级与全部下级；同一标段只统计一次。
         */
        private BigDecimal tenderControlPrice = BigDecimal.ZERO;
        /**
         * Excel S列金额，等于本科目含下级的合同签订价，不使用概算替代。
         */
        private BigDecimal contractOrEstimateAmount = BigDecimal.ZERO;
        /**
         * 自筹子表：首行为小计，其后为实际年份及可选的未填申请日期金额。
         */
        private List<PaidAmount> selfPaidDtls = new ArrayList<>();
        /**
         * 国拨子表：首行为小计，其后为实际年份及可选的未填申请日期金额。
         */
        private List<PaidAmount> governmentPaidDtls = new ArrayList<>();
        private List<ContractDetail> childNodes = new ArrayList<>();
    }

    /**
     * 自筹或国拨支付子表行，小计包括年度金额及未填申请日期金额。
     */
    @Data
    public static class PaidAmount {
        /**
         * SUBTOTAL：小计；YEAR：年度；UNDATED：未填申请日期。
         */
        private String type;
        /**
         * YEAR 行的实际支付申请年份，其他行为空。
         */
        private Integer year;
        private String label;
        /**
         * 流程结束的 Pay_Payable 汇总；自筹取来源 02，国拨取来源 01 或空值。
         */
        private BigDecimal amount = BigDecimal.ZERO;
    }

    /**
     * 数据库按科目及申请年份聚合的直接支付额，仅供服务内部组装使用。
     */
    @Data
    public static class SubjectPayment {
        private String bddId;
        private Integer year;
        private BigDecimal amount;
    }
}
