package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.RepaymentApplicationMapper;
import com.jpmsoft.gmgws.pojo.entity.RepaymentApplication;
import com.jpmsoft.gmgws.service.RepaymentApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepaymentApplicationServiceImpl  extends ServiceImpl<RepaymentApplicationMapper, RepaymentApplication> implements RepaymentApplicationService {
    @Override
    public List<RepaymentApplication> paymentHistory(List<String> cIds) {
        LambdaQueryWrapper<RepaymentApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RepaymentApplication::getLaId,cIds);
        queryWrapper.eq(RepaymentApplication::getState,7);
        return  baseMapper.selectList(queryWrapper);
    }
}
