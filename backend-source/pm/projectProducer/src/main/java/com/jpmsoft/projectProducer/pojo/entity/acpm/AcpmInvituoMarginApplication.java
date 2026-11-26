package com.jpmsoft.projectProducer.pojo.entity.acpm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投拓保证金申请
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:32
 */
@Data
@TableName("Acpm_InvituoMarginApplication")
public class AcpmInvituoMarginApplication {

    @TableId(type = IdType.INPUT)
    private String id;
    private String acpId;
    private String imaCode;
    @TableField(value = "Ima_BidBondMoney")
    private BigDecimal imaBidBondMoney;
    private String imaPayee;
    private String imaPayeeBank;
    private String imaPayeeCaName;
    private String imaPayeeCa;
    private String imaRemark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private Integer state;
    private Date stateTime;
    private String teId;
}
