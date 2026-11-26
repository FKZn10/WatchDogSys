package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 投资估算分解变更
 */
@Data
@TableName("Investment_Estimate_Breakdown_change")
public class InvestmentEstimateBreakdownChange extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4093748915803055317L;
    private String id;
    private String projectId;
    // 原申请id
    private String originApplyId;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
