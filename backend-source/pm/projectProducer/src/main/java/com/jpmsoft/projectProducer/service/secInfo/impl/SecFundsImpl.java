package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecFundsDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecFundsMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFunds;
import com.jpmsoft.projectProducer.service.secInfo.ISecFundsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/19 8:42
 */
@Service
public class SecFundsImpl extends ServiceImpl<SecFundsMapper, SecFunds> implements ISecFundsService {


    @Override
    public void createOrUpdate(List<SecFundsDTO> data, String secId) {
        if (!CollectionUtils.isEmpty(data)) {
            List<SecFunds> olds = getBySecId(secId);
            List<SecFunds> adds = new ArrayList<>();
            if (CollectionUtils.isEmpty(olds)) {
                data.forEach(d -> {
                    SecFunds entity = new SecFunds();
                    BeanUtils.copyProperties(d, entity);
                    entity.setSecId(secId);
                    entity.setId(GenerateUUID.getUUID());
                    adds.add(entity);
                });
            } else {
                List<SecFunds> updates = new ArrayList<>();
                List<String> oldIds = olds.stream().map(SecFunds::getId).collect(Collectors.toList());
                List<String> newIds = data.stream().filter(e -> ValidateUtil.isNotEmpty(e.getId())).map(SecFundsDTO::getId).collect(Collectors.toList());
                List<String> removeIds = oldIds.stream().filter(id -> !newIds.contains(id)).collect(Collectors.toList());
                for (SecFundsDTO d : data) {
                    SecFunds entity = new SecFunds();
                    BeanUtils.copyProperties(d, entity);
                    entity.setSecId(secId);
                    entity.setId(ValidateUtil.isEmpty(d.getId()) ? GenerateUUID.getUUID() : d.getId());
                    if (ValidateUtil.isEmpty(d.getId()) || !oldIds.contains(d.getId())) adds.add(entity);
                    else updates.add(entity);
                }
                if (!CollectionUtils.isEmpty(removeIds)) removeBatchByIds(removeIds);
                if (!CollectionUtils.isEmpty(updates)) updateBatchById(updates);
            }
            if (!CollectionUtils.isEmpty(adds)) saveBatch(adds);
        }
    }

    @Override
    public List<SecFundsDTO> selectBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId)) return new ArrayList<>();
        QueryWrapper<SecFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        wrapper.orderByAsc("Sf_Sort");
        List<SecFunds> entities = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(entities)) return new ArrayList<>();
        List<SecFundsDTO> datas = new ArrayList<>();
        entities.forEach(entity -> {
            SecFundsDTO data = new SecFundsDTO();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }

    @Override
    public void deleteBySecId(String secId) {
        QueryWrapper<SecFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteByBpId(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) return;
        baseMapper.deleteByBpId(bpId);
    }

    private List<SecFunds> getBySecId(String secId) {
        QueryWrapper<SecFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        return baseMapper.selectList(wrapper);
    }
}
