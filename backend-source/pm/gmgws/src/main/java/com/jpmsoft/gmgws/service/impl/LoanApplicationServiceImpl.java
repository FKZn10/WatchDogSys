package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.LoanApplicationMapper;
import com.jpmsoft.gmgws.pojo.entity.LoanApplication;
import com.jpmsoft.gmgws.service.LoanApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationServiceImpl extends ServiceImpl<LoanApplicationMapper, LoanApplication> implements LoanApplicationService {
    @Override
    public List<LoanApplication> getLoan(String uid) {
        LambdaQueryWrapper<LoanApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoanApplication::getCreateUid,uid);
        queryWrapper.eq(LoanApplication::getState,7);
        return baseMapper.selectList(queryWrapper);
    }
}
