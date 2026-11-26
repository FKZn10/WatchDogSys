package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 评标专家构成
 */
@Data
@TableName("Sec_Bid_Evaluation_Expert")
public class SecBidEvaluationExpert {
    @TableId(type = IdType.INPUT)
    private String beeId;
    private String secId;
    private String beeEvaluationMethod;
    private Date beeEvaluationTime;
    private String beeExpertApplication;
    private String beeExpertStatement;
    private String beeExpertGign;
    private String beeExpertFeeCollection;
    private String beeCreateUid;
    private Date beeCreateTime;
    private String beeCreateUname;
    private Integer beeState;
    private Date beeStateTime;
    private String teId;
}
