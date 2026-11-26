package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.dto.ExpenseClaimsDTO;
import com.jpmsoft.gmgws.pojo.dto.TravelExpenseFormDTO;
import com.jpmsoft.gmgws.pojo.entity.TravelExpenseForm;

import java.util.List;

public interface TravelExpenseFormService   extends IService<TravelExpenseForm> {
   List<TravelExpenseForm> businessAmt(List<String> ids);


   List<TravelExpenseFormDTO>  selectByErIds(List<String> erIds);
}
