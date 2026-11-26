package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.RepaymentApplicationDtlMapper;
import com.jpmsoft.gmgws.pojo.entity.RepaymentApplicationDtl;
import com.jpmsoft.gmgws.service.RepaymentApplicationDtlService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepaymentApplicationDtlServiceImpl  extends ServiceImpl<RepaymentApplicationDtlMapper, RepaymentApplicationDtl> implements RepaymentApplicationDtlService {
    @Override
    public List<RepaymentApplicationDtl> getDeductionAmt(List<String> ids) {
        LambdaQueryWrapper<RepaymentApplicationDtl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RepaymentApplicationDtl::getErId,ids);
        return baseMapper.selectList(queryWrapper);
    }
}
