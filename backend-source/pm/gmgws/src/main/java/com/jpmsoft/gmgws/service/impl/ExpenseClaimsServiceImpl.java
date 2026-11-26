package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.gmgws.mapper.ExpenseClaimsMapper;
import com.jpmsoft.gmgws.pojo.dto.ExpenseClaimsDTO;
import com.jpmsoft.gmgws.pojo.entity.ExpenseClaims;
import com.jpmsoft.gmgws.service.ExpenseClaimsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseClaimsServiceImpl extends ServiceImpl<ExpenseClaimsMapper, ExpenseClaims> implements ExpenseClaimsService {
    @Override
    public List<ExpenseClaims> getRecord(List<String> cIds) {
        LambdaQueryWrapper<ExpenseClaims> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ExpenseClaims::getErId, cIds);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ExpenseClaimsDTO> selectByErIds(List<String> erIds) {
        QueryWrapper<ExpenseClaims> wrapper = new QueryWrapper<>();
        wrapper.in("er_id", erIds);
        List<ExpenseClaims> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().map(d -> {
                ExpenseClaimsDTO e = new ExpenseClaimsDTO();
                BeanUtils.copyProperties(d, e);
                return e;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
