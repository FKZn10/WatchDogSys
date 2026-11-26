package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yb.Sun       投资估算分解
 * @date 2024/10/25 15:39
 */
@Data
@TableName("Investment_Estimate_Breakdown")
public class InvestmentEstimateBreakdown extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4093748915803055317L;
    private String id;
    private String projectId;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    // 估算总金额
    private BigDecimal totalInvestment;
    // 是否需要概算审核  01 是  02 否
    private String estReview;

    private String investMentFile;


}
