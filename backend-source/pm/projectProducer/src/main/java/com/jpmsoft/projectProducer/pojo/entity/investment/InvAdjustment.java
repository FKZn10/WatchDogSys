package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调整事项
 */
@Data
@TableName("Inv_Adjustment")
public class InvAdjustment {

    @TableId(type = IdType.INPUT)
    private String iaId;
    private String pId;
    private String conId;
    private String tId;
    private String iaType;
    private String remark;
    private BigDecimal totalAmount;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
