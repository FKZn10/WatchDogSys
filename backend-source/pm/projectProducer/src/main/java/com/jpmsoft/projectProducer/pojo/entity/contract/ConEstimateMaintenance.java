package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同概算维护表
 */
@Data
@TableName("Con_Estimate_Maintenance")
public class ConEstimateMaintenance {

    @TableId(type = IdType.INPUT)
    private String cemId;
    private String pId;
    private String conId;
    private String secId;
    private String cemType;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private String UpdateUserId;
    private String UpdateUserName;
    private Date updateUserTime;
    private Integer state;
    private Date aprrovalEndTime;
}
