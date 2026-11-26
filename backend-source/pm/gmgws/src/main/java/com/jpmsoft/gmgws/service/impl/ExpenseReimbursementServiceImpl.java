package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.mapper.ContractStatisticsMapper;
import com.jpmsoft.gmgws.mapper.ExpenseReimbursementMapper;
import com.jpmsoft.gmgws.pojo.dto.ExpenseClaimsDTO;
import com.jpmsoft.gmgws.pojo.dto.ExpenseReimbursementDTO;
import com.jpmsoft.gmgws.pojo.dto.TravelExpenseFormDTO;
import com.jpmsoft.gmgws.pojo.entity.ContractStatistics;
import com.jpmsoft.gmgws.pojo.entity.ExpenseReimbursement;
import com.jpmsoft.gmgws.service.ContractStatisticsService;
import com.jpmsoft.gmgws.service.ExpenseClaimsService;
import com.jpmsoft.gmgws.service.ExpenseReimbursementService;
import com.jpmsoft.gmgws.service.TravelExpenseFormService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseReimbursementServiceImpl  extends ServiceImpl<ExpenseReimbursementMapper, ExpenseReimbursement> implements ExpenseReimbursementService {

    @Resource
    private ExpenseClaimsService expenseClaimsService;
    @Resource
    private TravelExpenseFormService travelExpenseFormService;

    @Override
    public List<ExpenseReimbursement> reimbursement(String uid) {
        LambdaQueryWrapper<ExpenseReimbursement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpenseReimbursement::getState,7);
        queryWrapper.eq(ExpenseReimbursement::getCreateUid,uid);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public BaseResponse getExpenseReimbursementList(ExpenseReimbursementDTO request) {
        IPage<ExpenseReimbursementDTO> page=new Page<>(request.getPage(),request.getSize());
        List<ExpenseReimbursementDTO> data=baseMapper.getExpenseReimbursementList(page,request);
        if (!CollectionUtils.isEmpty(data))
        {
            List<ExpenseClaimsDTO> expenseClaims=expenseClaimsService.selectByErIds(data.stream().map(ExpenseReimbursementDTO::getId).collect(Collectors.toList()));
            List<TravelExpenseFormDTO> travelExpenseForms=travelExpenseFormService.selectByErIds(data.stream().map(ExpenseReimbursementDTO::getId).collect(Collectors.toList()));
            data.forEach(d->{
                d.setExpenseClaims(expenseClaims.stream().filter(f->f.getErId().equals(d.getId())).collect(Collectors.toList()));
                d.setTravelExpenseForms(travelExpenseForms.stream().filter(f->f.getErId().equals(d.getId())).collect(Collectors.toList()));
            });
        }
        return BaseResponse.ok(data,Long.valueOf(page.getTotal()).intValue());
    }
}
