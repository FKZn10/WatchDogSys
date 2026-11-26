package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

import java.util.List;

@Data
public class EFAPostResponse {
    private String status;
    private String paySerialNum;
    private List<EFAPostResponseDetails> details;
}
