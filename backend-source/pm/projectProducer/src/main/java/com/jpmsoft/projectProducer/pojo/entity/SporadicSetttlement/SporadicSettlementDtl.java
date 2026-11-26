package com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sporadic_settlement_dtl")
public class SporadicSettlementDtl {
    @TableId("id")
    private String id; // 主键ID
    private String createUid; // 创建人ID
    private String createUname; // 创建人姓名
    private Date createTime; // 创建时间
    private Integer state; // 状态
    private Date stateTime; // 状态更新时间
    private String seId; // 关联结算单ID
    @JsonProperty("pName")
    private String pName; // 项目名称
    @JsonProperty("pNumber")
    private String pNumber; // 项目编号
    private Date seSettlement; // 结算金额
    private String mrId; // 关联ID

}