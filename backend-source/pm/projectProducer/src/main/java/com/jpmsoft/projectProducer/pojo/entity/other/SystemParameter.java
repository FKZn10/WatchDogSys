package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("system_parameter")
public class SystemParameter {
    private String id;  // 主键
    private BigDecimal minPaymentAmount;  // 拨付最小金额
    private BigDecimal minPaymentRatio;  // 拨付最低比例
    private LocalDateTime progressDeclarationStart;  // 进度申报每月开始日期
    private LocalDateTime progressDeclarationEnd;  // 进度申报每月结束日期
    private LocalDateTime progressApprovalEnd;  // 进度审批每月结束日期
    private LocalDateTime settlementAuditStart;  // 结算审计每月开始日期
    private LocalDateTime settlementAuditEnd;  // 结算审计每月结束日期
    private LocalDateTime fundingAllocationStart;  // 资金拨付每月开始日期
    private LocalDateTime fundingAllocationEnd;  // 资金拨付每月结束日期
    private String mobileVerificationCode;  // 手机端校验码
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
