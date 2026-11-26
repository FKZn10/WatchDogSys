package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计变更记录单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 8:34
 */
@Data
@TableName("Con_Dcls")
public class ConDcls {

    @TableId(type = IdType.INPUT)
    private String dclsId;
    private String pId;
    private String conId;
    private String dclsCode;
    private String dclsProposedUnit;
    private Date dclsTime;
    private String dclsName;
    private String dclsMajor;
    private String dclsChangeReason;
    private String dclsPreChange;
    private String dclsModifiedScheme;
    private BigDecimal dclsPreTotal;
    private BigDecimal dclsModifiedTotal;
    private BigDecimal dclsFirstTotal;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String dcoId;
    private String dcbId;
    private String teId;
}
