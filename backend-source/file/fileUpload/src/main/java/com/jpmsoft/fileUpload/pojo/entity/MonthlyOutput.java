package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Monthly_Output ")
public class MonthlyOutput {

    @TableId("id")
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private String conId;
    private String moYear;
    private String moMonth;
    private BigDecimal moJhMoney;
    private BigDecimal moMoney;
}
