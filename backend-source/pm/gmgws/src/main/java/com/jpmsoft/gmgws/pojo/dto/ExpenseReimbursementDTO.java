package com.jpmsoft.gmgws.pojo.dto;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/*
经费报销
 */
@Data
public class ExpenseReimbursementDTO extends BaseFileRequest {

    private String id;
    private String createUid;
    private String createUname;
    private LocalDateTime createTime;
    private Integer state;
    private LocalDateTime stateTime;
    private String erDepartment;
    private String erApplicantr;
    private Integer erCategory;
    private BigDecimal erMoneyTotal;
    private String erCode;
    private String erIsLoan;
    private String erYswjFile;
    private String erHtFile;
    private String erCgwjFile;
    private String erFpjcyFile;
    private String erSqqsFile;
    private String erJswjFile;

    private String erDepartmentName;
    private List<ExpenseClaimsDTO> expenseClaims;
    private List<TravelExpenseFormDTO> travelExpenseForms;
}
