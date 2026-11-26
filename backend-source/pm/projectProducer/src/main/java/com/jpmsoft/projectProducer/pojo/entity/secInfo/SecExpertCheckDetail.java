package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

// 抽取的专家表  和  考核评分表 的中间表

@Data
@TableName("Sec_Expert_Check_Detail")
public class SecExpertCheckDetail {

    @TableId
    private String id;

    // 专家选取 id
    private String srId;

    // 专家考核评分 id
    private String ecId;

    // 扣分值
    private BigDecimal totalDeduction;

    //专家选取记录详情 id
    private String srdId;

    // 专家 id
    private String exId;

    // 专家选取记录详情结果
    private Integer secdResult;

    private String selection;

    private Integer selected;

    // 01 作废 其他为未作废
    private String status;
}
