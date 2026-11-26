package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目立项
 */
@Data
@TableName("Pre_Pro_Approval")
public class PreProApproval {
    @TableId(type = IdType.INPUT)
    private String paId;
    private String pId;
    private String paName;
    private String paCode;
    private String paType;
    private String paScale;
    private String paOverview;
    private String paConstructionUnitId;
    private Date paPlanStart;
    private Date paPlanEnd;
    private String paPmId;
    private String paPmName;
    private String paPmTel;
    private String paNature;
    private BigDecimal paTotalInv;
    private String paRemark;
    private Integer paState;
    private Date paStateTime;
    private Date paCreateTime;
    private String paCreateUid;
    private String paCreateUname;
    private String paGroupDoc;
    private String paSasacDoc;
    private String paReformDoc;
    private Integer paIsParticipate;
    private String teId;
}
