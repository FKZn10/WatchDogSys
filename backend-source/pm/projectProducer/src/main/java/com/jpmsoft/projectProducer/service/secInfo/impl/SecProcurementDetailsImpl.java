package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.model.request.SecInfo.SecProcurementDetailsDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProcurementDetailsMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementDetails;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/17 13:49
 */
@Service
public class SecProcurementDetailsImpl extends ServiceImpl<SecProcurementDetailsMapper, SecProcurementDetails> implements ISecProcurementDetailsService {
    @Resource
    private FileFeign fileFeign;

    @Override
    public void createOrUpdate(List<SecProcurementDetailsDTO> data, String secId) {
        if (!CollectionUtils.isEmpty(data)) {
            List<SecProcurementDetails> olds = getBySecId(secId);
            List<SecProcurementDetails> adds = new ArrayList<>();
            List<RefershFileDto> files = new ArrayList<>();
            if (CollectionUtils.isEmpty(olds)) {
                data.forEach(d -> {
                    SecProcurementDetails entity = new SecProcurementDetails();
                    BeanUtils.copyProperties(d, entity);
                    entity.setId(GenerateUUID.getUUID());
                    entity.setSecId(secId);
                    adds.add(entity);
                    files.add(file(d, entity.getId()));
                });
            } else {
                List<SecProcurementDetails> updates = new ArrayList<>();
                List<String> oldIds = olds.stream().map(SecProcurementDetails::getId).collect(Collectors.toList());
                List<String> newIds = data.stream().filter(e -> ValidateUtil.isNotEmpty(e.getId())).map(SecProcurementDetailsDTO::getId).collect(Collectors.toList());
                List<String> removeIds = oldIds.stream().filter(id -> !newIds.contains(id)).collect(Collectors.toList());
                for (SecProcurementDetailsDTO d : data) {
                    SecProcurementDetails entity = new SecProcurementDetails();
                    BeanUtils.copyProperties(d, entity);
                    entity.setSecId(secId);
                    entity.setId(ValidateUtil.isEmpty(d.getId()) ? GenerateUUID.getUUID() : d.getId());
                    files.add(file(d, entity.getId()));
                    if (ValidateUtil.isEmpty(d.getId()) || !oldIds.contains(d.getId())) adds.add(entity);
                    else updates.add(entity);
                }
                if (!CollectionUtils.isEmpty(removeIds)) removeBatchByIds(removeIds);
                if (!CollectionUtils.isEmpty(updates)) updateBatchById(updates);
            }
            if (!CollectionUtils.isEmpty(adds)) saveBatch(adds);
            if (!CollectionUtils.isEmpty(files)) fileFeign.refreshRelationFileBath(files);
        }
    }

    @Override
    public List<SecProcurementDetailsDTO> selectBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId)) return new ArrayList<>();
        QueryWrapper<SecProcurementDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        wrapper.orderByAsc("Pd_Sort");
        List<SecProcurementDetails> entities = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(entities)) return new ArrayList<>();
        List<SecProcurementDetailsDTO> datas = new ArrayList<>();
        entities.forEach(entity -> {
            SecProcurementDetailsDTO data = new SecProcurementDetailsDTO();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }

    @Override
    public void deleteBySecId(String secId) {
        QueryWrapper<SecProcurementDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteByBpId(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) return;
        baseMapper.deleteByBpId(bpId);
    }

    private RefershFileDto file(SecProcurementDetailsDTO d, String id) {
        RefershFileDto file = new RefershFileDto();
        file.setRelationIds(Arrays.asList(d.getPdOtherFile(), d.getPdProofFile(), d.getPdOtherImg()));
        file.setFileIds(Arrays.asList(d.getPdOtherFileIds(), d.getPdProofFileIds(), d.getPdOtherImgIds()));
        file.setEntityId(id);
        return file;
    }

    private List<SecProcurementDetails> getBySecId(String secId) {
        QueryWrapper<SecProcurementDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        return baseMapper.selectList(wrapper);
    }
}
