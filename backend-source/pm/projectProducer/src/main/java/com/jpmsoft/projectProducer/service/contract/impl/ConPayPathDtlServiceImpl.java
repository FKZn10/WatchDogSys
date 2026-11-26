package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.ConPayPathDtlRequest;
import com.jpmsoft.common.model.response.pay.ConPayPathDtlResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConPayPathDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayPathDtl;
import com.jpmsoft.projectProducer.service.contract.IConPayPathDtlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConPayPathDtlServiceImpl extends ServiceImpl<ConPayPathDtlMapper, ConPayPathDtl> implements IConPayPathDtlService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConPayPathDtl(List<ConPayPathDtlRequest> conPayPathDtls, String payId) {
        this.remove(Wrappers.<ConPayPathDtl>lambdaQuery().eq(ConPayPathDtl::getPayId, payId));
        if (ValidateUtil.isEmpty(conPayPathDtls)) {
            return BaseResponse.ok();
        }
        List<ConPayPathDtl> entities = conPayPathDtls.stream().map(request -> {
            ConPayPathDtl entity = new ConPayPathDtl();
            BeanUtils.copyProperties(request, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setPayId(payId);
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(entities);
        return BaseResponse.ok();
    }

    @Override
    public List<ConPayPathDtlResponse> getConPayPathDtlByPayId(String payId) {
        List<ConPayPathDtl> entities = this.list(Wrappers.<ConPayPathDtl>lambdaQuery().eq(ConPayPathDtl::getPayId, payId));
        if (ValidateUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(entity -> {
            ConPayPathDtlResponse response = new ConPayPathDtlResponse();
            BeanUtils.copyProperties(entity, response);
            return response;
        }).collect(Collectors.toList());
    }
}
