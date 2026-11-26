package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专家论证会申请
 */
@Data
@TableName("Con_Ecm")
public class ConEcm {

    @TableId(type = IdType.INPUT)
    private String ecmId;
    /**
     * 项目ID
     */
    private String pId;
    /**
     * 变更洽商单ID
     */
    private String enisId;
    /**
     * 设计变更单ID
     */
    private String dcoId;
    /**
     * 变更名称
     */
    private String ecmName;
    /**
     * 变更原因、责任分类说明
     */
    private String ecmType;
    /**
     * 分类其它因素描述
     */
    private String ecmOtherNote;
    /**
     * 工程变更原因及依据
     */
    private String ecmReason;
    /**
     * 工程变更范围及内容
     */
    private String ecmContent;
    /**
     * 变更估算金额
     */
    private BigDecimal ecmAmount;

    private String ecmCreateUid;
    private String ecmCreateUname;
    private Date ecmCreateTime;
    private Integer ecmState;
    private Date ecmStateTime;
    private Boolean ecmYesNo;
    private Date ecmAdoptTime;
    private Boolean ecmIsHistory;
    private String ecmDep;
    private String ecmApprovalMatter;
    private String teId;
}
