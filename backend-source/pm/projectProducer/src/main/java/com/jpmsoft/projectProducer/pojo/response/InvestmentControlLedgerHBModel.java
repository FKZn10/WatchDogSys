package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentControlLedgerHBModel {
    private String bddId;
    // 概算申报主表id
    private String bdId;
    // 序号
    private String bddNumber;
    // 上级ID
    private String bddParentId;
    // 清单编码
    private String bddCode;
    // 项目及费用名称
    private String bddName;
    // 计量单位名称
    private String bddUnit;
    // 工程量
    private Float bddCount;
    // 单价
    private BigDecimal bddPrice;
    // 合价（投资）
    private BigDecimal bddTotal;
    private String bddRemark;
    // 排序号
    private Integer bddOrder;
    //【预留字段】层级
    private Integer bddLevel;
    //【预留字段】类别
    private Integer bddType;
    //【预留字段】状态
    private Integer bddState;
    //【预留字段】上级路径
    private String bddPath;
    //【预留字段】是否叶子节点
    private Integer bddLeafNode;
    private String bddOldId;
    // 建筑工程费
    private BigDecimal bddBuildFee;
    // 安装工程费
    private BigDecimal bddInstallFee;
    // 设备购置费
    private BigDecimal bddDeviceFee;
    // 其他费用
    private BigDecimal bddOtherFee;
    private BigDecimal reserveCost;
    private BigDecimal bddRate;

    private String conId;
    private String conName;
    private BigDecimal conMoneyTotal;
    /**
     * 报备变更
     */
    private BigDecimal ecApplyMoney;
    /**
     * 确定变更
     */
    private BigDecimal ekrSdje;
    /**
     * 变更累计
     */
    private BigDecimal changeCumulative;
    /**
     * 变更+合同
     */
    private BigDecimal changeConCumulative;
    /**
     * 变更后占合同比例
     */
    // private BigDecimal changeConRate;
    /**
     * 累计产值
     */
    private BigDecimal investment;
    /**
     * 累计付款金额
     */
    private BigDecimal payApply;
    /**
     * 付款比例
     */
    // private BigDecimal payRate;
}
