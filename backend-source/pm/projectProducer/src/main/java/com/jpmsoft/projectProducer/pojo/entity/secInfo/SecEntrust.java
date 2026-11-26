package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 直接委托
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/31 10:59
 */
@Data
@TableName("Sec_Entrust")
public class SecEntrust extends BaseEntity {

    /**
     * 标段ID
     */
    private String secId;
    private String seResult;
    /**
     * 委托单位ID
     */
    private String seCpyId;
    private BigDecimal seAmt;
    private String seRemark;
    private String sePm;
    private String seQualification;
    private String seAchievement;
    private String teId;
}
