package com.jpmsoft.common.model.requestAndResponse.dh;

import lombok.Data;

@Data
public class MonthlyInspectionReportQdDTO {
    private String id;
    private String mirId;
    private String code;
    private String quantity;
    private String completeQuantity;
    private String totalCompleteQuantity;
    private String fileId;
    private String name;
}
