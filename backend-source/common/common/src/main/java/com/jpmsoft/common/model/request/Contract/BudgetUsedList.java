package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

@Data
public class BudgetUsedList {
    public String SystemCode;
    public String BudgetCode;
    public String BudgetAmount;
    public String CurrencyCode;
    public String TakeAmountType;
    public String ReportTypeCode;
    public String ReportTypeDetailName;
}
