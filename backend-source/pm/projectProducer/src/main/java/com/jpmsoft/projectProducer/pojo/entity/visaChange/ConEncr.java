package com.jpmsoft.projectProducer.pojo.entity.visaChange;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程洽商变更记录
 */
@Data
@TableName("Con_Encr")
public class ConEncr {

    @TableId(type = IdType.INPUT)
    private String encrId;
    /**
     * 工程量核实ID
     */
    private String envId;
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
    private String encrCode;
    /**
     * 工程名称
     */
    private String encrProjectName;
    /**
     * 专业名称
     */
    private String encrMajor;
    /**
     * 提出单位
     */
    private String encrProposeId;
    /**
     * 变更名称
     */
    private String encrTitle;
    /**
     * 变更原因
     */
    private String encrReason;
    /**
     * 变更前方案
     */
    private String encrSchemeBefore;
    /**
     * 变更后方案
     */
    private String encrSchemeAfter;
    /**
     * 变更前预算总价
     */
    private BigDecimal encrTotalBefore;
    /**
     * 变更后预算总价
     */
    private BigDecimal encrTotalAfter;
    /**
     * 初审预算总价
     */
    private BigDecimal encrTotalFirstTrial;
    /**
     * 日期
     */
    private Date encrDate;

    private String encrCreateUid;
    private String encrCreateUname;
    private Date encrCreateTime;
    private Integer encrState;
    private Date encrStateTime;
    private String enisId;
    private String teId;

}
