package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.gmgws.mapper.TravelExpenseFormMapper;
import com.jpmsoft.gmgws.pojo.dto.ExpenseClaimsDTO;
import com.jpmsoft.gmgws.pojo.dto.TravelExpenseFormDTO;
import com.jpmsoft.gmgws.pojo.entity.ExpenseClaims;
import com.jpmsoft.gmgws.pojo.entity.TravelExpenseForm;
import com.jpmsoft.gmgws.service.TravelExpenseFormService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelExpenseFormServiceImpl  extends ServiceImpl<TravelExpenseFormMapper, TravelExpenseForm> implements TravelExpenseFormService {

    @Override
    public List<TravelExpenseForm> businessAmt(List<String> ids) {
        LambdaQueryWrapper<TravelExpenseForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TravelExpenseForm::getErId,ids);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TravelExpenseFormDTO> selectByErIds(List<String> erIds) {
        QueryWrapper<TravelExpenseForm> wrapper = new QueryWrapper<>();
        wrapper.in("er_id", erIds);
        List<TravelExpenseForm> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().map(d -> {
                TravelExpenseFormDTO e = new TravelExpenseFormDTO();
                BeanUtils.copyProperties(d, e);
                return e;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
