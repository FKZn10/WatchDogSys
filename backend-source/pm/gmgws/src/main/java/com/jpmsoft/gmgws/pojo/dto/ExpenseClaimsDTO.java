package com.jpmsoft.gmgws.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 经费报销事项
 */
@Data
public class ExpenseClaimsDTO {
    private String id;
    private String createUid;
    private String createUname;
    private LocalDateTime createTime;
    private Integer state;
    private LocalDateTime stateTime;
    private String erId;
    private String ecUse;
    private BigDecimal ecReimbursementAmount;
    private String ecConId;
    private BigDecimal ecMoney;
    private String ecRemark;

}
