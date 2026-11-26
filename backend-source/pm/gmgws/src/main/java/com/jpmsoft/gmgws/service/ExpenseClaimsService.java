package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.dto.ExpenseClaimsDTO;
import com.jpmsoft.gmgws.pojo.entity.ContractStatistics;
import com.jpmsoft.gmgws.pojo.entity.ExpenseClaims;

import java.util.List;

public interface ExpenseClaimsService  extends IService<ExpenseClaims> {
    List<ExpenseClaims> getRecord(List<String> cIds);


    List<ExpenseClaimsDTO>  selectByErIds(List<String> erIds);
}
