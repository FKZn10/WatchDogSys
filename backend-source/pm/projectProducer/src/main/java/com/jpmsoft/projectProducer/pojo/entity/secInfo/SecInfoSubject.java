package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Sec_Info_Subject")
public class SecInfoSubject {
    @TableId(type = IdType.INPUT)
    private String id;
    private String secId;
    /**
     * 估算科目id
     */
    private String subIebId;
    /**
     * 概算科目id
     */
    private String subIbbId;
    private BigDecimal subMoney;

    private String levelCode;
}