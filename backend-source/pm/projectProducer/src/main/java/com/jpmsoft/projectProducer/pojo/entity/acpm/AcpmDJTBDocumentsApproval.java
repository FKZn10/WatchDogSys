package com.jpmsoft.projectProducer.pojo.entity.acpm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代建投标文件审批
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:53
 */
@Data
@TableName("Acpm_DJTBDocumentsApproval")
public class AcpmDJTBDocumentsApproval {

    @TableId(type = IdType.INPUT)
    private String id;
    private String acpId;
    private String daCode;
    @TableField(value = "Da_BidBondMoney")
    private BigDecimal daBidBondMoney;
    private String daArea;
    @TableField(value = "Da_TenderClosingTime")
    private Date daTenderClosingTime;
    private String daAuthorizedAgent;
    private String daAuthorizedAgentId;
    @TableField(value = "Da_SpecialInstructions")
    private String daSpecialInstructions;
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
