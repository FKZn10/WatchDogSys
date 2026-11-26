package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("engineering_keep_record")
public class EngineeringKeepRecord {
    @TableId
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String erId;
    private String ecId;
    private String pId;
    private String conId;
    private Date ekrDate;
    private String ekrBgsx;
    private String ekrBgsy;
    private BigDecimal ekrSdje;
    private BigDecimal ekrHtljbgje;
    private BigDecimal ekrZhtjebl;
    private String ekrYsshyj;
    private String ekrGcbghqd;
    private String ekrGcbgdad;
}