package com.jpmsoft.jpbpm.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("Sec_Bidding_Processing")
public class SecBiddingProcessing {

    @TableId(type = IdType.INPUT)
    private String id;
    private String secId;
    private String tCode;
    private Integer state;
    private Date startTime;
    private Date endTime;
    private String notes;
}
