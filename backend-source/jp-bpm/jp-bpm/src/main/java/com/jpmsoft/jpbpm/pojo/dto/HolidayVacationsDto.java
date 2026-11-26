package com.jpmsoft.jpbpm.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class HolidayVacationsDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date startTime;//开始时间，calculationType为1和2时必传
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date endTime;//结束时间，calculationType为1和3时必传
    public int number;//加减值。单位根据type
    public String calculationType;//1:两时间差，2：往后计算多少天，3：往前计算多少天
    public String type;//计算类型，hour:小时，day：天;
    public  String festivalHoliday = "1";//"是否排除节假日",1：不排除，2：排除
    public String workTimeType = "ALL";//是否计算上下班时间
    public  String workShift = "";//上班时间
    public  String closingTime = "";//下班时间
    public  String noonBreak = "1";//是否午休
    public  String noonBreakStartTime = "12:00:00";//午休开始时间
    public  String noonBreakEndTime = "12:00:00";//午休结束时间

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFestivalHoliday() {
        return festivalHoliday;
    }

    public void setFestivalHoliday(String festivalHoliday) {
        this.festivalHoliday = festivalHoliday;
    }

    public String getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(String workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getWorkShift() {
        return workShift;
    }

    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getNoonBreak() {
        return noonBreak;
    }

    public void setNoonBreak(String noonBreak) {
        this.noonBreak = noonBreak;
    }

    public String getNoonBreakStartTime() {
        return noonBreakStartTime;
    }

    public void setNoonBreakStartTime(String noonBreakStartTime) {
        this.noonBreakStartTime = noonBreakStartTime;
    }

    public String getNoonBreakEndTime() {
        return noonBreakEndTime;
    }

    public void setNoonBreakEndTime(String noonBreakEndTime) {
        this.noonBreakEndTime = noonBreakEndTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }
}
