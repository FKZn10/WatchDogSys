package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "Con_Pay_Apply")
@Data
public class ConPayApply {
    @TableId(value = "Cpa_Id", type = IdType.INPUT)
    public String cpaId;
    public String pId;
    public String conId;
    //支付类型
    public String cpaType;
    //数据来源
    public String cpaSources;
    //申请日期
    public Date cpaApplyDate;
    public String cpaApplyUid;
    //本期批复金额
    public BigDecimal cpaApprovedAmount;
    //当前支付金额
    public BigDecimal cpaPaymentAmount;
    //支付比例
    public String cpaPaymentProportion;
    public String cpaRemarks;
    public Date cpaCreateTime;
    //合同支付/代建预拨id
    public String cpaForeign;
    private String teId;
}
