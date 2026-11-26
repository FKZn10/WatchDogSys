package com.jpmsoft.common.model.response.Maintenance;


import lombok.Data;

import java.util.Date;

@Data
public class SporadicSettlementDtlResponse {
    private String id; // 主键ID
    private String createUid; // 创建人ID
    private String createUname; // 创建人姓名
    private Date createTime; // 创建时间
    private Integer state; // 状态
    private Date stateTime; // 状态更新时间
    private String seId; // 关联结算单ID
    private String pName; // 项目名称
    private String pNumber; // 项目编号
    private Date seSettlement; // 结算金额
    private String mrId; // 关联ID

}