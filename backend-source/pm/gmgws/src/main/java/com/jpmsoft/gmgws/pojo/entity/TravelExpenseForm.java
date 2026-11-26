package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 差旅报销单
 */
@Data
@TableName("Travel_Expense_Form")
public class TravelExpenseForm {

    @TableId(value = "id")
    private String id;

    @TableField("Create_Uid")
    private String createUid;

    @TableField("Create_Uname")
    private String createUname;

    @TableField("Create_Time")
    private LocalDateTime createTime;

    @TableField("State")
    private Integer state;

    @TableField("State_Time")
    private LocalDateTime stateTime;

    @TableField("Er_Id")
    private String erId;

    @TableField("Tef_Departure_Time")
    private LocalDateTime tefDepartureTime;

    @TableField("Tef_Departure_Point")
    private String tefDeparturePoint;

    @TableField("Tef_Reach_Time")
    private LocalDateTime tefReachTime;

    @TableField("Tef_Reach_Point")
    private String tefReachPoint;

    @TableField("Tef_People_Number")
    private Integer tefPeopleNumber;

    @TableField("Tef_Business_Trip")
    private BigDecimal tefBusinessTrip;

    @TableField("Tef_Intercity_Transportation")
    private BigDecimal tefIntercityTransportation;

    @TableField("Tef_Accommodation_Cost")
    private BigDecimal tefAccommodationCost;

    @TableField("Tef_Food_Allowance")
    private BigDecimal tefFoodAllowance;

    @TableField("Tef_City_Fare")
    private BigDecimal tefCityFare;

    @TableField("Tef_Other")
    private BigDecimal tefOther;

    @TableField("Tef_Toal")
    private BigDecimal tefToal;

    @TableField("Tef_Remark")
    private String tefRemark;
}
