package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资格预审
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:14
 */
@Data
@TableName("Sec_QE_Pre_Trial")
public class SecQePreTrial extends BaseEntity {

    private String secId;
    private String sqtValuationMethod;
    private BigDecimal sqtAmount;
    private String sqtRemarks;
    private String sqtOtherPricing;
}
