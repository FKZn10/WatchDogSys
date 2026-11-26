package com.jpmsoft.gmgws.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 差旅报销单
 */
@Data
public class TravelExpenseFormDTO {

    private String id;
    private String createUid;
    private String createUname;
    private LocalDateTime createTime;
    private Integer state;
    private LocalDateTime stateTime;
    private String erId;
    private LocalDateTime tefDepartureTime;
    private String tefDeparturePoint;
    private LocalDateTime tefReachTime;
    private String tefReachPoint;
    private Integer tefPeopleNumber;
    private BigDecimal tefBusinessTrip;
    private BigDecimal tefIntercityTransportation;
    private BigDecimal tefAccommodationCost;
    private BigDecimal tefFoodAllowance;
    private BigDecimal tefCityFare;
    private BigDecimal tefOther;
    private BigDecimal tefToal;
    private String tefRemark;
}
