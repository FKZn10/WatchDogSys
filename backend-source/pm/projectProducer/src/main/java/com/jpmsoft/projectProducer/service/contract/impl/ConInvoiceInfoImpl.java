package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConInvoiceInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoiceInfo;
import com.jpmsoft.projectProducer.service.contract.IConInvoiceInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票签收-发票信息
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:32
 */
@Service
public class ConInvoiceInfoImpl extends ServiceImpl<ConInvoiceInfoMapper, ConInvoiceInfo> implements IConInvoiceInfoService {
    @Override
    public void createOrUpdate(List<ConInvoiceInfoDTO> data, String isId) {
        deleteByIsId(isId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConInvoiceInfo> entities = data.stream().map(d -> {
                ConInvoiceInfo e = new ConInvoiceInfo();
                BeanUtils.copyProperties(d, e);
                e.setId(GenerateUUID.getUUID());
                e.setIsId(isId);
                return e;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void deleteByIsId(String isId) {
        QueryWrapper<ConInvoiceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("Is_Id", isId);
        baseMapper.delete(wrapper);
    }
}
