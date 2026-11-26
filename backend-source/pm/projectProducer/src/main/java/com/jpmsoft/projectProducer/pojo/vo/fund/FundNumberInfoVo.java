package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FundNumberInfoVo extends BaseFileDto {
    private String id;
    private String pId;
    private String pname;
    private String fnName;
    private String fnCode;
    private String prId;
    private String prCode;
    private BigDecimal fnAmount;
    //余额
    private BigDecimal fnBalance;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date fnReceiptVerificationDate;
}
