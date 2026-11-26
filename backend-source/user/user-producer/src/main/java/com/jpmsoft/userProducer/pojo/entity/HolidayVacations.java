package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("HolidayVacations")
public class HolidayVacations {
    @TableId(type = IdType.INPUT)
    public String id;
    public Integer hvYear;
    public Integer hvMonth;
    public Date hvDay;
    public Integer hvWeeks;
    public Integer hvDays;
    public Integer hvWeek;
    public Integer hvWeekend;
    public Integer hvWeekday;
    public Integer hvHolidayvacations;
    public Integer hvCompensatory;
    public Integer hvLegalholiday;
    public Integer hvFestivalday;
    public Date hvSynchronizationtime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHvYear() {
        return hvYear;
    }

    public void setHvYear(Integer hvYear) {
        this.hvYear = hvYear;
    }

    public Integer getHvMonth() {
        return hvMonth;
    }

    public void setHvMonth(Integer hvMonth) {
        this.hvMonth = hvMonth;
    }

    public Date getHvDay() {
        return hvDay;
    }

    public void setHvDay(Date hvDay) {
        this.hvDay = hvDay;
    }

    public Integer getHvWeeks() {
        return hvWeeks;
    }

    public void setHvWeeks(Integer hvWeeks) {
        this.hvWeeks = hvWeeks;
    }

    public Integer getHvDays() {
        return hvDays;
    }

    public void setHvDays(Integer hvDays) {
        this.hvDays = hvDays;
    }

    public Integer getHvWeek() {
        return hvWeek;
    }

    public void setHvWeek(Integer hvWeek) {
        this.hvWeek = hvWeek;
    }

    public Integer getHvWeekend() {
        return hvWeekend;
    }

    public void setHvWeekend(Integer hvWeekend) {
        this.hvWeekend = hvWeekend;
    }

    public Integer getHvWeekday() {
        return hvWeekday;
    }

    public void setHvWeekday(Integer hvWeekday) {
        this.hvWeekday = hvWeekday;
    }

    public Integer getHvHolidayvacations() {
        return hvHolidayvacations;
    }

    public void setHvHolidayvacations(Integer hvHolidayvacations) {
        this.hvHolidayvacations = hvHolidayvacations;
    }

    public Integer getHvCompensatory() {
        return hvCompensatory;
    }

    public void setHvCompensatory(Integer hvCompensatory) {
        this.hvCompensatory = hvCompensatory;
    }

    public Integer getHvLegalholiday() {
        return hvLegalholiday;
    }

    public void setHvLegalholiday(Integer hvLegalholiday) {
        this.hvLegalholiday = hvLegalholiday;
    }

    public Integer getHvFestivalday() {
        return hvFestivalday;
    }

    public void setHvFestivalday(Integer hvFestivalday) {
        this.hvFestivalday = hvFestivalday;
    }

    public Date getHvSynchronizationtime() {
        return hvSynchronizationtime;
    }

    public void setHvSynchronizationtime(Date hvSynchronizationtime) {
        this.hvSynchronizationtime = hvSynchronizationtime;
    }
}




