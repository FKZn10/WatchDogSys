package com.jpmsoft.common.model.requestAndResponse.PeInfo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PeInfoRankingModel {

    private String constructionUnit;
    private String performingUnit;
    private Integer peYear;
    private Integer peQuarter;
    private BigInteger conNumber;
    private BigDecimal conTotalPrice;
    private String conName;
    private Float averageScore;
    private BigInteger ranking;
    private BigDecimal peAdministrativeSanction;
    private BigDecimal pePetitionRecord;

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getPerformingUnit() {
        return performingUnit;
    }

    public void setPerformingUnit(String performingUnit) {
        this.performingUnit = performingUnit;
    }

    public Integer getPeYear() {
        return peYear;
    }

    public void setPeYear(Integer peYear) {
        this.peYear = peYear;
    }

    public Integer getPeQuarter() {
        return peQuarter;
    }

    public void setPeQuarter(Integer peQuarter) {
        this.peQuarter = peQuarter;
    }

    public BigInteger getConNumber() {
        return conNumber;
    }

    public void setConNumber(BigInteger conNumber) {
        this.conNumber = conNumber;
    }

    public BigDecimal getConTotalPrice() {
        return conTotalPrice;
    }

    public void setConTotalPrice(BigDecimal conTotalPrice) {
        this.conTotalPrice = conTotalPrice;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public Float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Float averageScore) {
        this.averageScore = averageScore;
    }

    public BigInteger getRanking() {
        return ranking;
    }

    public void setRanking(BigInteger ranking) {
        this.ranking = ranking;
    }

    public BigDecimal getPeAdministrativeSanction() {
        return peAdministrativeSanction;
    }

    public void setPeAdministrativeSanction(BigDecimal peAdministrativeSanction) {
        this.peAdministrativeSanction = peAdministrativeSanction;
    }

    public BigDecimal getPePetitionRecord() {
        return pePetitionRecord;
    }

    public void setPePetitionRecord(BigDecimal pePetitionRecord) {
        this.pePetitionRecord = pePetitionRecord;
    }
}
