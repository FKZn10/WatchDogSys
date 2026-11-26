package com.jpmsoft.projectProducer.pojo.entity.performanceClaim;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖罚单
 */
@Data
@TableName("Pc_Award_Ticket")
public class PcAwardTicket {

    @TableId(type = IdType.INPUT)
    private String atId;
    private String pId;
    private String conId;
    private String atCId;
    private BigDecimal atMoney;
    private String atReason;
    private String atContent;

    /**
     * 奖罚单类型
     * @see com.jpmsoft.projectProducer.constants.AtTypeEnum
     */
    private Integer atType;
    private String atCreateUid;
    private String atCreateUname;
    private Date atCreateTime;
    private Integer atState;
    private Date atStateTime;
    private String payId;

    private Date atLssueTime;
    private Date atDeductTime;
    private String atRectification;
    private String atRemark;
    private String atCode;
    private String teId;

    /**
     * 登记状态
     * @see com.jpmsoft.projectProducer.constants.MarkStateEnum
     */
    private String markState;

}
