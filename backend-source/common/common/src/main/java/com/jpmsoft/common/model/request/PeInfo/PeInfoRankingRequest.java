package com.jpmsoft.common.model.request.PeInfo;


import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

/**
 * 履约评价排名
 */
public class PeInfoRankingRequest extends BaseModeRequest {

    private String peType;
    private Integer state;
    private List<String> conType;
    private Integer peQuarter;
    private Integer year;


    public String getPeType() {
        return peType;
    }

    public void setPeType(String peType) {
        this.peType = peType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<String> getConType() {
        return conType;
    }

    public void setConType(List<String> conType) {
        this.conType = conType;
    }

    public Integer getPeQuarter() {
        return peQuarter;
    }

    public void setPeQuarter(Integer peQuarter) {
        this.peQuarter = peQuarter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
