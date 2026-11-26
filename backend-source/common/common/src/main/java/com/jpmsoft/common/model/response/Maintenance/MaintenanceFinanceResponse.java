package com.jpmsoft.common.model.response.Maintenance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MaintenanceFinanceResponse {

    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String mfSource;
    private String mfCode;
    private String mrId;
    private BigDecimal mfBudget;
}
