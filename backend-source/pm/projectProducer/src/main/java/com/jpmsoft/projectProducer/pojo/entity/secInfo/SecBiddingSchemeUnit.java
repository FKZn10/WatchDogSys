package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 报名单位情况
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:09
 */
@Data
@TableName("Sec_Bidding_Scheme_Unit")
public class SecBiddingSchemeUnit {

    @TableId(type = IdType.INPUT)
    private String id;
    private String bsId;
    private String cName;
    private String bsuQualifications;
    private BigDecimal bsuOffer;
    private String bsuPerformance;
    private String bsuOther;
    private String bsuFile;
}
