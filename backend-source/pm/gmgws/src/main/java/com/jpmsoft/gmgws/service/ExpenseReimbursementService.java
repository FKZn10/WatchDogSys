package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.dto.ExpenseReimbursementDTO;
import com.jpmsoft.gmgws.pojo.entity.ExpenseReimbursement;

import java.util.List;

public interface ExpenseReimbursementService  extends IService<ExpenseReimbursement> {
    List<ExpenseReimbursement> reimbursement(String uid);

    BaseResponse getExpenseReimbursementList(ExpenseReimbursementDTO request);
}
