package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付申请
 */
@Data
@TableName("BG_ConUnitOrder")
public class BGConUnitOrder {
    @TableId(type = IdType.INPUT)
    private String id;
    private String cuoCode;

    @TableField("CUO_DYCompanyId")
    private String cuoDYCompanyId;
    @TableField("CUO_OrderDate")
    private Date cuoOrderDate;
    private String cuoType;
    @TableField("CUO_PId")
    private String cuoPId;
    @TableField("CUO_ConId")
    private String cuoConId;
    @TableField("CUO_FSCompanyId")
    private String cuoFSCompanyId;
    private String cuoContent;
    private BigDecimal cuoMoney;
    @TableField("CUO_BL")
    private BigDecimal cuoBL;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;

    // 最终确认金额
    private BigDecimal finalConfirmMoney;

}
