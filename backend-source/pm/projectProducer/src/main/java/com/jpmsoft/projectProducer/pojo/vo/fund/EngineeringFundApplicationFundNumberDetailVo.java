package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 工程用款申请文号占用明细表Vo
 */
@Data
public class EngineeringFundApplicationFundNumberDetailVo {
    private String id;
    private String efaId;
    private BigDecimal efaOccupancyAmount;
    private String fnId;
    private String fnName;
    private String fnCode;
    private String prId;
    private String prCode;
    private BigDecimal fnAmount;
    private BigDecimal fnBalance;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date fnReceiptVerificationDate;

}
