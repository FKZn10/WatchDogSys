package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 现场签证变更记录
 */
@Data
@TableName("Con_Svcr")
public class ConSvcr {

    @TableId(type = IdType.INPUT)
    private String svcrId;
    /**
     * 现场签证工程量核实Id
     */
    private String svvId;
    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同ID
     */
    private String conId;
    /**
     * 编号
     */
    private String svcrCode;
    /**
     * 工程名称
     */
    private String svcrProjectName;
    /**
     * 专业名称
     */
    private String svcrMajor;
    /**
     * 提出单位ID
     */
    private String svcrProposeId;
    /**
     * 时间
     */
    private Date svcrDate;
    /**
     * 内容摘要
     */
    private String svcrContentSummary;
    /**
     * 签证内容
     */
    private String svcrContent;
    /**
     * 变更前方案
     */
    private String svcrSchemeBefore;
    /**
     * 变更后方案
     */
    private String svcrSchemeAfter;
    /**
     * 变更前预算总价
     */
    private BigDecimal svcrTotalBefore;
    /**
     * 变更后预算总价
     */
    private BigDecimal svcrTotalAfter;
    /**
     * 初审预算总价
     */
    private BigDecimal svcrTotalFirstTrial;
    private String svcrCreateUid;
    private String svcrCreateUname;
    private Date svcrCreateTime;
    private Integer svcrState;
    private Date svcrStateTime;
    private String svisId;
    private String teId;
}
