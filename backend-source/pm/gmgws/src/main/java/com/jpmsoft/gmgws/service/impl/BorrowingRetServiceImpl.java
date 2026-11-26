package com.jpmsoft.gmgws.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.pojo.entity.*;
import com.jpmsoft.gmgws.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BorrowingRetServiceImpl implements BorrowingRetService {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private RepaymentApplicationService repaymentApplicationService;

    @Autowired
    private ExpenseClaimsService expenseClaimsService;

    @Autowired
    private TravelExpenseFormService travelExpenseFormService;

    @Autowired
    private ExpenseReimbursementService expenseReimbursementService;

    @Autowired
    private RepaymentApplicationDtlService repaymentApplicationDtlService;
    @Override
    public BaseResponse getLoanStill(String uid) {

        List<LoanApplication> list = loanApplicationService.getLoan(uid);
        List<Map<String,Object>> data=  new ArrayList<>();
        if(ValidateUtil.isNotEmpty(list)){
             List<String> cIds = list.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<RepaymentApplication> rep = repaymentApplicationService.paymentHistory(cIds);
            for (LoanApplication tion : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",tion.getId());
                map.put("laDepartment",tion.getLaDepartment());
                map.put("laBorrower",tion.getLaBorrower());
                map.put("loanAmount",tion.getLaBorrowerMoney());  //借款金额
                     BigDecimal repayment = rep.stream().filter(e -> ValidateUtil.isNotNull(e.getLaId()) && e.getLaId().equals(tion.getId()))
                            .map(RepaymentApplication::getRaTotalRepayment)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                     map.put("repayment",repayment);  //合计还款
                data.add(map);
            }
        }
        return BaseResponse.ok(data);
    }

    //报销
    @Override
    public BaseResponse getReimbursement(String uid) {
       List<ExpenseReimbursement> list =  expenseReimbursementService.reimbursement(uid);
       List<Map<String,Object>> data = new ArrayList<>();
       if(ValidateUtil.isNotEmpty(list)){
            List<String> cIds = list.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<ExpenseClaims> claList = expenseClaimsService.getRecord(cIds);
            List<TravelExpenseForm> trList = travelExpenseFormService.businessAmt(cIds);
           List<RepaymentApplication> rep = repaymentApplicationService.paymentHistory(cIds);
           List<String> crIds = rep.stream().map(e -> e.getId()).collect(Collectors.toList());
           List<RepaymentApplicationDtl> deductionAmtList = new ArrayList<>();
           if(ValidateUtil.isNotEmpty(crIds)){
                deductionAmtList = repaymentApplicationDtlService.getDeductionAmt(crIds);
           }
           for (ExpenseReimbursement exp : list) {
               Map<String,Object> map = new HashMap<>();
               map.put("id",exp.getId());
               BigDecimal reduce = claList.stream().filter(e -> ValidateUtil.isNotNull(e.getEcMoney()) && e.getErId().equals(exp.getId()))
                       .map(ExpenseClaims::getEcMoney)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
               map.put("reiAmount",reduce); //报销金额
                BigDecimal tefToal = trList.stream().filter(e -> ValidateUtil.isNotNull(e.getTefToal()) && e.getErId().equals(exp.getId()))
                       .map(TravelExpenseForm::getTefToal)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
                map.put("tefToal",tefToal);  //小计汇总

               BigDecimal deductionAmt = deductionAmtList.stream().filter(e -> ValidateUtil.isNotNull(e.getRadDeductionThis()) && e.getErId().equals(exp.getId()))
                       .map(RepaymentApplicationDtl::getRadDeductionThis)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
               map.put("deductionAmt",deductionAmt);  //抵扣金额
               map.put("category",exp.getErCategory());  //类型
               data.add(map);
           }
       }
        return BaseResponse.ok(data);
    }
}
