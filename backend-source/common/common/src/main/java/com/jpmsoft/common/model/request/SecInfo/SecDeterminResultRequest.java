package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 定标结果
 */
public class SecDeterminResultRequest extends BaseFileRequest {

    private String drId;
    private String secId;
    private String drRemark;
    private String drCalibrationType;
    private Date drCalibrationDate;
    private String drCalibrationMode;
    private Integer drNumberVacancies;
    private String drBidWinner;
    private String drCreateUid;
    private String drCreateUname;
    private Date drCreateTime;
    private Integer drState;
    private Date drStateTime;
    private String drPreferential;
    private String drSelectedFactor;
    private String drCalibrateFactor;

    private Integer drRank;
    private String drSupplierName;
    private BigDecimal drSupplierPrice;
    private BigDecimal drSupplierScore;
    private BigDecimal drSupplierBid;
    private Integer drCandidate;
    private String drAgentName;
    private Date drPrTime;
    private Date drBidTime;
    private String drLocation;
    private String drProcurementName;
    private String drSuccessfulName;
    private BigDecimal drSuccessfulAmt;
    private String drSuccessfulTxtAmt;
    private String drSupplierContacts;
    private String drSupplierPhone;
    private String drThirdId;




    private String secName;
    private String drBidWinnerName;
    private List<String> drCalibrationDates;
    private List<String> drCreateTimes;
    private List<String> drVacancies;
    private List<SecDeterminCpyDto> secDeterminCpys;

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getDrRemark() {
        return drRemark;
    }

    public void setDrRemark(String drRemark) {
        this.drRemark = drRemark;
    }

    public String getDrCalibrationType() {
        return drCalibrationType;
    }

    public void setDrCalibrationType(String drCalibrationType) {
        this.drCalibrationType = drCalibrationType;
    }

    public Date getDrCalibrationDate() {
        return drCalibrationDate;
    }

    public void setDrCalibrationDate(Date drCalibrationDate) {
        this.drCalibrationDate = drCalibrationDate;
    }

    public String getDrCalibrationMode() {
        return drCalibrationMode;
    }

    public void setDrCalibrationMode(String drCalibrationMode) {
        this.drCalibrationMode = drCalibrationMode;
    }

    public Integer getDrNumberVacancies() {
        return drNumberVacancies;
    }

    public void setDrNumberVacancies(Integer drNumberVacancies) {
        this.drNumberVacancies = drNumberVacancies;
    }

    public String getDrBidWinner() {
        return drBidWinner;
    }

    public void setDrBidWinner(String drBidWinner) {
        this.drBidWinner = drBidWinner;
    }

    public String getDrCreateUid() {
        return drCreateUid;
    }

    public void setDrCreateUid(String drCreateUid) {
        this.drCreateUid = drCreateUid;
    }

    public String getDrCreateUname() {
        return drCreateUname;
    }

    public void setDrCreateUname(String drCreateUname) {
        this.drCreateUname = drCreateUname;
    }

    public Date getDrCreateTime() {
        return drCreateTime;
    }

    public void setDrCreateTime(Date drCreateTime) {
        this.drCreateTime = drCreateTime;
    }

    public Integer getDrState() {
        return drState;
    }

    public void setDrState(Integer drState) {
        this.drState = drState;
    }

    public Date getDrStateTime() {
        return drStateTime;
    }

    public void setDrStateTime(Date drStateTime) {
        this.drStateTime = drStateTime;
    }

    public String getDrPreferential() {
        return drPreferential;
    }

    public void setDrPreferential(String drPreferential) {
        this.drPreferential = drPreferential;
    }

    public String getDrSelectedFactor() {
        return drSelectedFactor;
    }

    public void setDrSelectedFactor(String drSelectedFactor) {
        this.drSelectedFactor = drSelectedFactor;
    }

    public String getDrCalibrateFactor() {
        return drCalibrateFactor;
    }

    public void setDrCalibrateFactor(String drCalibrateFactor) {
        this.drCalibrateFactor = drCalibrateFactor;
    }

    public Integer getDrRank() {
        return drRank;
    }

    public void setDrRank(Integer drRank) {
        this.drRank = drRank;
    }

    public String getDrSupplierName() {
        return drSupplierName;
    }

    public void setDrSupplierName(String drSupplierName) {
        this.drSupplierName = drSupplierName;
    }

    public BigDecimal getDrSupplierPrice() {
        return drSupplierPrice;
    }

    public void setDrSupplierPrice(BigDecimal drSupplierPrice) {
        this.drSupplierPrice = drSupplierPrice;
    }

    public BigDecimal getDrSupplierScore() {
        return drSupplierScore;
    }

    public void setDrSupplierScore(BigDecimal drSupplierScore) {
        this.drSupplierScore = drSupplierScore;
    }

    public BigDecimal getDrSupplierBid() {
        return drSupplierBid;
    }

    public void setDrSupplierBid(BigDecimal drSupplierBid) {
        this.drSupplierBid = drSupplierBid;
    }

    public Integer getDrCandidate() {
        return drCandidate;
    }

    public void setDrCandidate(Integer drCandidate) {
        this.drCandidate = drCandidate;
    }

    public String getDrAgentName() {
        return drAgentName;
    }

    public void setDrAgentName(String drAgentName) {
        this.drAgentName = drAgentName;
    }

    public Date getDrPrTime() {
        return drPrTime;
    }

    public void setDrPrTime(Date drPrTime) {
        this.drPrTime = drPrTime;
    }

    public Date getDrBidTime() {
        return drBidTime;
    }

    public void setDrBidTime(Date drBidTime) {
        this.drBidTime = drBidTime;
    }

    public String getDrLocation() {
        return drLocation;
    }

    public void setDrLocation(String drLocation) {
        this.drLocation = drLocation;
    }

    public String getDrProcurementName() {
        return drProcurementName;
    }

    public void setDrProcurementName(String drProcurementName) {
        this.drProcurementName = drProcurementName;
    }

    public String getDrSuccessfulName() {
        return drSuccessfulName;
    }

    public void setDrSuccessfulName(String drSuccessfulName) {
        this.drSuccessfulName = drSuccessfulName;
    }

    public BigDecimal getDrSuccessfulAmt() {
        return drSuccessfulAmt;
    }

    public void setDrSuccessfulAmt(BigDecimal drSuccessfulAmt) {
        this.drSuccessfulAmt = drSuccessfulAmt;
    }

    public String getDrSuccessfulTxtAmt() {
        return drSuccessfulTxtAmt;
    }

    public void setDrSuccessfulTxtAmt(String drSuccessfulTxtAmt) {
        this.drSuccessfulTxtAmt = drSuccessfulTxtAmt;
    }

    public String getDrSupplierContacts() {
        return drSupplierContacts;
    }

    public void setDrSupplierContacts(String drSupplierContacts) {
        this.drSupplierContacts = drSupplierContacts;
    }

    public String getDrSupplierPhone() {
        return drSupplierPhone;
    }

    public void setDrSupplierPhone(String drSupplierPhone) {
        this.drSupplierPhone = drSupplierPhone;
    }

    public String getDrThirdId() {
        return drThirdId;
    }

    public void setDrThirdId(String drThirdId) {
        this.drThirdId = drThirdId;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getDrBidWinnerName() {
        return drBidWinnerName;
    }

    public void setDrBidWinnerName(String drBidWinnerName) {
        this.drBidWinnerName = drBidWinnerName;
    }

    public List<String> getDrCalibrationDates() {
        return drCalibrationDates;
    }

    public void setDrCalibrationDates(List<String> drCalibrationDates) {
        this.drCalibrationDates = drCalibrationDates;
    }

    public List<String> getDrCreateTimes() {
        return drCreateTimes;
    }

    public void setDrCreateTimes(List<String> drCreateTimes) {
        this.drCreateTimes = drCreateTimes;
    }

    public List<String> getDrVacancies() {
        return drVacancies;
    }

    public void setDrVacancies(List<String> drVacancies) {
        this.drVacancies = drVacancies;
    }

    public List<SecDeterminCpyDto> getSecDeterminCpys() {
        return secDeterminCpys;
    }

    public void setSecDeterminCpys(List<SecDeterminCpyDto> secDeterminCpys) {
        this.secDeterminCpys = secDeterminCpys;
    }
}
