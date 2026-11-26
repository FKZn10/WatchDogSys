package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同计量计价
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@Data
@TableName("Con_Measure_Price")
public class ConMeasurePrice {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 创建人
     */
    private String createUid;

    /**
     * 创建人
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 流程状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 项目id
     */
    private String pId;

    /**
     * 合同id
     */
    private String conId;

    /**
     * 计量期数
     */
    private Integer cmpPeriod;

    /**
     * 申报开始时间
     */
    private Date cmpDeclareStartTime;

    /**
     * 申报结束时间
     */
    private Date cmpDeclareEndTime;

    /**
     * 本期工程费合计
     */
    private BigDecimal cmpCurrentAmount;

    /**
     * 累计工程费合计
     */
    private BigDecimal cmpAccumulatedAmount;

    /**
     * 应付款合计
     */
    private BigDecimal cmpPayableAmount;

    /**
     * 应扣款合计
     */
    private BigDecimal cmpDeductibleAmount;

    /**
     * 实际支付
     */
    private BigDecimal cmpActualPayment;

    /**
     * 备注
     */
    private String cmpRemark;

    private String cmpType;
}