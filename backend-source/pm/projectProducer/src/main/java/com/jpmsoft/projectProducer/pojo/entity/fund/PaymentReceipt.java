package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Payment_Receipt")
public class PaymentReceipt extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2013195941638930535L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
	//收款项目
    private String projectId;
	//收据编码
	private String prCode;
	/** 付款单位 */
	private String prPayCId;
	/** 收款金额 */
	private BigDecimal prAmount;
	/** 收款方式 */
	private String prMethod;
	/** 收款备注 */
	private String prRemark;
	/** 线下开具状态
	 * 申请中 0
	 * 未开具 1
	 * 已开具 2
	 * 已作废 3
	 * 已回收 4
	 * 已停止 5
	 * 无需开具 6
	 * */
	private Integer prIssuedState;
	/** 核销状态
	 * 申请中 0
	 * 未核销 1
	 * 核销成功 2
	 * 核销退回 3
	 * 已停止 4
	 * 核销退回 5
	 * */
	private Integer prVerifyState;
	/** 核销日期 */
	private Date prVerifyTime;
	/** 开具日期 */
	private Date prIssuedTime;
	/** 作废日期 */
	private Date prCancelTime;
	/** 回收日期 */
	private Date prRecoveryTime;
	/** 退回原因 */
	private String prRollbackReason;
	//收款金额（大写）
    private String prAmountChinese;
	//收款单位
    private String prCName;
	/** 是否需要开具线下收据
	 * 否 0
	 * 是 1
	 * */
	private Integer isPayIssued;

    private String prIsClaim;

    /**
     * cifdId
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String cifId;

	/**
	 * 收据未核销额
	 */
	private BigDecimal receiptUnwrittenAmount;
}
