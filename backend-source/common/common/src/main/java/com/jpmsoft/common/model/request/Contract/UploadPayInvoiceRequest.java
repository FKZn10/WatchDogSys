package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

@Data
public class UploadPayInvoiceRequest {

    // 支付id
    private String payId;

    // 发票附件
    private String invoiceFileRelationId;

    private String invoiceFileRelationIdFileIds;

    private String fileCode;

    private String tCode;

    private String fileTittle;

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }
}
