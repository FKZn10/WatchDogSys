package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 重点专家库使用申请
 */
@Data
public class SecFocusExpertDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 流程状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 项目id
     */
    @JsonProperty("pId")
    private String pId;

    /**
     * 标段id
     */
    private String secId;

    /**
     * 启用条件1
     */
    @JsonProperty("conditionA")
    private String conditionA;

    /**
     * 启用条件2
     */
    @JsonProperty("conditionB")
    private String conditionB;

    /**
     * 启用条件3
     */
    @JsonProperty("conditionC")
    private String conditionC;

    /**
     * 启用条件4
     */
    @JsonProperty("conditionD")
    private String conditionD;

    /**
     * 认定依据1
     */
    @JsonProperty("accordingA")
    private String accordingA;

    /**
     * 认定依据1依据标准
     */
    @JsonProperty("accordingADetails")
    private String accordingADetails;

    /**
     * 认定依据2
     */
    @JsonProperty("accordingB")
    private String accordingB;

    /**
     * 认定依据2依据标准
     */
    @JsonProperty("accordingBDetails")
    private String accordingBDetails;

    /**
     * 认定依据2的补充
     */
    @JsonProperty("accordingC")
    private String accordingC;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 拟抽取人数
     */
    private Integer extractNumber;

    /**
     * 专家论证附件
     */
    private String expertDemonstration;

    /**
     * 招标内容
     */
    private String secDetails;

    /**
     * 标段名称
     */
    private String secName;

    /**
     * 项目名称
     */
    @JsonProperty("pName")
    private String pName;

    /**
     * 估算金额
     */
    private String secMoney;

    /**
     * 项目概况
     */
    @JsonProperty("pOverview")
    private String pOverview;
}
