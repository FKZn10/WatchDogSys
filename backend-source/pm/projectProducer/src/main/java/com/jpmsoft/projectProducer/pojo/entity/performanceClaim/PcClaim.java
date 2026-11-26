package com.jpmsoft.projectProducer.pojo.entity.performanceClaim;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 索赔
 */
@Data
@TableName("Pc_Claim")
public class PcClaim {

    @TableId(type = IdType.INPUT)
    private String claimId;
    private Integer claimType;
    private String pId;
    private String conId;
    private String claimFp;
    private String claimSp;
    private BigDecimal claimConMoney;
    private String claimConState;
    private String claimTitle;
    private String claimContent;
    private String claimRemark;
    private String claimCreateUid;
    private String claimCreateUname;
    private Date claimCreateTime;
    private Integer claimState;
    private Date claimStateTime;
    private String teId;

}
