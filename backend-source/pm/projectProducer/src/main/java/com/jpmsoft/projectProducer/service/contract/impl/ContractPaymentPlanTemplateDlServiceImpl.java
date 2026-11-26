package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ContractPaymentPlanTemplateDlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentPlanTemplateDl;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractResultVo;
import com.jpmsoft.projectProducer.service.contract.ContractPaymentPlanTemplateDlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractPaymentPlanTemplateDlServiceImpl extends ServiceImpl<ContractPaymentPlanTemplateDlMapper, ContractPaymentPlanTemplateDl> implements ContractPaymentPlanTemplateDlService {
    @Override
    public void delContractTemplateDl(String id) {
        LambdaQueryWrapper<ContractPaymentPlanTemplateDl> dl = new LambdaQueryWrapper<>();
        dl.eq(ContractPaymentPlanTemplateDl::getTemplateId, id);
        baseMapper.delete(dl);
    }

    @Override
    public BaseResponse updateContractTemplateDl(ContractResultVo contractResultVo) {
        if (ValidateUtil.isEmpty(contractResultVo.getId())) {
            return BaseResponse.fail("缺少必要参数!");
        }
        if (ValidateUtil.isEmpty(contractResultVo.getTemplateId())) {
            return BaseResponse.fail("缺少必要参数!");
        }
        ContractPaymentPlanTemplateDl dl = new ContractPaymentPlanTemplateDl();
        dl.setUpdateTime(new Date());
        BeanUtils.copyProperties(contractResultVo, dl);
        baseMapper.updateById(dl);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse delContractTemplateDlId(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少必要参数!");
        }
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse saveContractPaymentPlanDlNode(ContractResultVo contractResultVo) {
        if (ValidateUtil.isEmpty(contractResultVo.getTemplateId())) {
            return BaseResponse.fail("缺少必要参数!");
        }
        ContractPaymentPlanTemplateDl dl = new ContractPaymentPlanTemplateDl();
        BeanUtils.copyProperties(contractResultVo, dl);
        dl.setId(GenerateUUID.getUUID());
        dl.setCreateTime(new Date());
        baseMapper.insert(dl);
        return BaseResponse.ok();
    }

    @Override
    public List<ContractPaymentPlanTemplateDl> getContrActDataDl(List<String> collect) {
        LambdaQueryWrapper<ContractPaymentPlanTemplateDl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ContractPaymentPlanTemplateDl::getTemplateId, collect);
        baseMapper.selectList(queryWrapper);
        return baseMapper.selectList(queryWrapper);
    }
}
