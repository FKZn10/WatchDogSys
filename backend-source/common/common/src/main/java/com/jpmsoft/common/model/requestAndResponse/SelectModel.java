package com.jpmsoft.common.model.requestAndResponse;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SelectModel {
    private String title;
    private String value;
    private Boolean check;
    private Boolean brand;
    private String brandValue;
    private List<SelectModel> children;
    private BigDecimal areaUsed;
    private BigDecimal areaAvailable;
    private Date startDate;
    private Date endDate;
}
