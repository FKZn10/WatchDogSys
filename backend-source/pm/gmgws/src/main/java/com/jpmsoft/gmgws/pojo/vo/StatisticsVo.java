package com.jpmsoft.gmgws.pojo.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StatisticsVo {

    private String contractName;  //合同名
    private String companyName;   //乙方单位名


    private String conId;

    private String projectName;  //项目名

    private String contractNumber;   //合同编号

    private List<String> contractAmts; // 合同金额数组
    private String contractAmtStr;  //合同金额开始
    private String contractAmtEnd;  //合同金额结束

    private List<String> paymentAmounts;  //付款合计数组
    private String paymentAmountStr;  //付款合计开始
    private String paymentAmountEnd;  //付款合计结束


    private List<String> paymentSpeeds;   //付款进度数组
    private BigDecimal paymentSpeedStr;  //付款进度开始
    private BigDecimal paymentSpeedEnd;  //付款进度结束

    private List<String> settlements; //结算价
    private String settlementStr; //结算价开始
    private String settlementEnd; //结算价开始

    private String page;

    private String size;





}
