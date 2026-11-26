package com.jpmsoft.gmgws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.gmgws.pojo.dto.ExpenseReimbursementDTO;
import com.jpmsoft.gmgws.pojo.entity.ExpenseReimbursement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpenseReimbursementMapper extends BaseMapper<ExpenseReimbursement> {


    List<ExpenseReimbursementDTO> getExpenseReimbursementList(@Param("page") IPage<ExpenseReimbursementDTO> page, @Param("params") ExpenseReimbursementDTO params);
}
