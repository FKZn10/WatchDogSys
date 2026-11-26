package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 资金计划下达
 */
@Data
@TableName("Inv_Fund_Release")
public class InvFundRelease {

    @TableId(type = IdType.INPUT)
    private String ifrId;
    private String ifrFileName;
    private String ifrReplyCode;
    private Date ifrReplyTime;
    private String ifrCreateUid;
    private String ifrCreateUname;
    private Date ifrCreateTime;
    private String ifrFile;
    private Integer state;
    private Date stateTime;
    private String teId;
}
