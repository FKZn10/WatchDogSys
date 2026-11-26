package com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sporadic_settlement")
public class SporadicSettlement {


    @TableId("id")
    private String id; // 主键ID
    private String createUid; // 创建人ID
    private String createUname; // 创建人姓名
    private Date createTime; // 创建时间
    private Integer state; // 状态
    private Date stateTime; // 状态更新时间
    private String seNumber; // 结算单号
    private String sePName; // 项目名称
    private String sePNumber; // 项目编号
    private String seUnitId; // 单位ID
    private BigDecimal seSubmitPrice; // 提交金额
    private String seSubmitName; // 提交人姓名
    private String seSubmitUserId; // 提交人ID
    private String seSubmitPhone; // 提交人电话
    private String seDepartment; // 部门ID
    private BigDecimal sePrice; // 结算金额
    private String seName; // 结算人姓名
    private String seUserId; // 结算人ID
    private String sePhone; // 结算人电话
    private BigDecimal seConAmount; // 合同金额
    private BigDecimal seSum; // 总结算金额
    private Date seDate; // 结算日期
    private String seSituation; // 结算情况说明
    private Date seAuditTime;//审核日期


}
