package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Market_Investment_Payment")
public class MarketInvestmentPayment {

    /** 主键 ID */
    private String id;

    /** 支付表 ID */
    private String payId;

    /** 创建人 ID */
    private String createUid;

    /** 创建人姓名 */
    private String createUname;

    /** 创建时间 */
    private Date createTime;

    /** 状态 */
    private Integer state;

    /** 是否合同 (Mip_Is_Con) */
    private Integer mipIsCon;

    /** 状态时间 */
    private Date stateTime;

    /** 项目 ID (P_Id) */
    private String pId;

    /** 合同 ID (Con_Id) */
    private String conId;

    /** 施工单位 */
    private String mipConstructionUnit;

    /** 资金来源 */
    private String mipSourceFunds;

    /** 上次投资额 */
    private BigDecimal mipInvestmentLast;

    /** 批准贷款额 */
    private BigDecimal mipApproveLoan;

    /** 累计释放额 */
    private BigDecimal mipCumulativeRelease;

    /** 合同金额 */
    private BigDecimal mipConMoney;

    /** 收款人 */
    private String mipConsignee;

    /** 开户行 */
    private String mipBank;

    /** 银行账号 */
    private String mipAccountNumber;

    /** 本次申请金额 */
    private BigDecimal mipAppliedAmount;

    /** 进度款说明 */
    private String mipProgressFunds;

    /** 合同编号/关联 ID */
    private String mipConId;

    /** 总投资额 */
    private BigDecimal mipInvestmentTotal;

    /** 编号/代码 */
    private String mipCode;


}
