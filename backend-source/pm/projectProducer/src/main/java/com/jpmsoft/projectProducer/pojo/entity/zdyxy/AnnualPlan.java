package com.jpmsoft.projectProducer.pojo.entity.zdyxy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("annual_plan")
public class AnnualPlan implements Serializable {
    private static final long serialVersionUID = 9050467792900923638L;
    /**
     *
     */
    private String id;

    /**
     * 年
     */
    private Integer year;

    /**
     * 预算金额
     */
    private BigDecimal preAmount;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 施工单位
     */
    private String constructionContractor;

    /**
     * 审计单位
     */
    private String auditUnit;
    private String dispatchUid;
    /**
     * 创建人
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态时间
     */
    private Date stateTime;

    /**
     * 备注
     */
    private String remark;

    private String conName;
    private Date startTime;
    private Date endTime;
}