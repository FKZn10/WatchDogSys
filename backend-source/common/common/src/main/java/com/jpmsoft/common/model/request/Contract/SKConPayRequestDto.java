package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SKConPayRequestDto {
   public String BillsPayOrderNo;
   public String PayType;
   public String PaymentDate;
   public String BusinessTypeCode;
   public String BusinessTypeName;
   public String DataSource="建管集团筑云";
   public String PayOrgCode;
   public String PayOrgName;
   public String PayAccountNo;
   public String PayChannel;
   public String PayAmount;
   public String PayCurrencyType="CNY";
   public String PayCurrencyName="人民币";
   public String WhetherUrgent;
   public String Remark;
   public String PayUse;
   public String CrossCurrency;
   public String WhetherGround;
   public String CreateByCode;
   public String CreateByName;
   public String ExtBusinessNo;
   public String SerialNo;
   public String CashFlow;
   public String ExpandJson;
   public PaymentRec PaymentRec;
//   public List<BudgetUsedList> BudgetUsedList;
//   public List<InstitutionList> InstitutionList;
//   public List<CashFlowItemList> CashFlowItemList;

}

