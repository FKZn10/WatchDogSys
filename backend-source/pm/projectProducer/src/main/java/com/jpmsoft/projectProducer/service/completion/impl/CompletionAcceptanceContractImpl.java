package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceContractDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.completion.CompletionAcceptanceContractMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceContract;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:37
 */
@Service
public class CompletionAcceptanceContractImpl extends ServiceImpl<CompletionAcceptanceContractMapper, CompletionAcceptanceContract>
        implements ICompletionAcceptanceContractService {

    @Override
    public void createOrUpdateCompletionAcceptanceContract(List<CompletionAcceptanceContractDto> datas, String caId) {
        delCompletionAcceptanceContractByCaId(caId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<CompletionAcceptanceContract> entitys = datas.stream().map(data -> {
                CompletionAcceptanceContract entity = new CompletionAcceptanceContract();
                BeanUtils.copyProperties(data, entity);
                entity.setCaId(caId);
                entity.setCacId(ValidateUtil.isEmpty(data.getCacId()) ? GenerateUUID.getUUID() : data.getCacId());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delCompletionAcceptanceContractByCaId(String caId) {
        QueryWrapper<CompletionAcceptanceContract> wrapper = new QueryWrapper<>();
        wrapper.eq("Ca_Id", caId);
        baseMapper.delete(wrapper);
    }
}
