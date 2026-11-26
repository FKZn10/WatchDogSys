package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 认质认价
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:45
 */
@Data
@TableName("Me_Quality_Price")
public class MeQualityPrice {

    @TableId(type = IdType.INPUT)
    private String qpId;
    private String pId;
    private String conId;
    private String qpFirstPartyRid;
    private String qpFirstPartyRname;
    private String qpContractorId;
    private String qpSupervisorId;
    private String qpInitiator;
    private String qpInitiatorName;
    private Date qpInitiationTime;
    private String qpTitle;
    private String qpReason;
    private String qpRemark;
    private String qpCreateUid;
    private String qpCreateUname;
    private Date qpCreateTime;
    private String qpState;
    private Date qpStateTime;
    private String teId;
}
