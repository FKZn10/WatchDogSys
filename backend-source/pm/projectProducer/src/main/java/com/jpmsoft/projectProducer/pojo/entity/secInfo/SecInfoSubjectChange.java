package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Sec_Info_Subject_Change")
public class SecInfoSubjectChange {
    @TableId(type = IdType.INPUT)
    private String id;
    private String sicId;
    private String secId;
    private String subIebId;
    private String subIbbId;
    private BigDecimal subMoney;
    private String levelCode;
}