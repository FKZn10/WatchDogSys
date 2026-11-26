package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

@Data
public class EFAPayInfoResponse {
    private String payer;
	private String payerBranchBank;
	private String payerAccountNo;
	private String receiver;
	private String receivBank;
	private String receivAccount;
	private String receivAccountNo;
	private String paymentAmount;
	private String payTime;
}
