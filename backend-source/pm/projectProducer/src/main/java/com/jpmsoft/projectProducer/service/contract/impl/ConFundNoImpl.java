package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.pay.ConFundNoDto;
import com.jpmsoft.common.model.response.pay.ConFundNoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConFundNoMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConFundNo;
import com.jpmsoft.projectProducer.service.contract.IConFundNoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/15 14:34
 */
@Service
public class ConFundNoImpl extends ServiceImpl<ConFundNoMapper, ConFundNo> implements IConFundNoService {
    @Override
    public void createOrUpdateConFundNo(List<ConFundNoDto> datas, String payId) {
        delConFundNoByPayId(payId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConFundNo> entitys = datas.stream().map(data -> {
                ConFundNo entity = new ConFundNo();
                BeanUtils.copyProperties(data, entity);
                entity.setPayId(payId);
                entity.setId(ValidateUtil.isNotEmpty(data.getId()) ? data.getId() : GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delConFundNoByPayId(String payId) {
        QueryWrapper<ConFundNo> wrapper = new QueryWrapper<>();
        wrapper.eq("Pay_Id", payId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConFundNoVo> getConFundByConIds(List<String> payIds) {
        if (CollectionUtils.isEmpty(payIds)) return null;
        QueryWrapper<ConFundNo> wrapper = new QueryWrapper<>();
        wrapper.in("Pay_Id", payIds);
        List<ConFundNo> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(d -> {
                ConFundNoVo r = new ConFundNoVo();
                BeanUtils.copyProperties(d, r);
                return r;
            }).collect(Collectors.toList());
        return null;
    }
}
