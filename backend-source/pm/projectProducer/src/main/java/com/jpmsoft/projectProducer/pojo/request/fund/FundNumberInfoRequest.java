package com.jpmsoft.projectProducer.pojo.request.fund;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.util.Date;

@Data
public class FundNumberInfoRequest extends BasePageEntity {
    private String id;
    private String pId;
    private String pName;
    private String fnName;
    private String fnCode;
    private String prID;
    private Double fnAmount;
    private Date fnReceiptVerificationDate;
}
