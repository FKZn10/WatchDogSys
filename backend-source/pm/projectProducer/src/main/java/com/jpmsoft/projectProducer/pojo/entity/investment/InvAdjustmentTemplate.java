package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 调整事项模板
 */
@Data
@TableName("Inv_Adjustment_Template")
public class InvAdjustmentTemplate {

    @TableId(type = IdType.INPUT)
    private String atId;
    private String atTitle;
    private String atType;
    private String remark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
