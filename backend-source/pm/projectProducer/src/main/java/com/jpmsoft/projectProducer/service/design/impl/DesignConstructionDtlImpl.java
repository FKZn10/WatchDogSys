package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConstructionDtlDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignConstructionDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConstructionDtl;
import com.jpmsoft.projectProducer.service.design.IDesignConstructionDtlService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/25 14:12
 */
@Service
public class DesignConstructionDtlImpl extends ServiceImpl<DesignConstructionDtlMapper, DesignConstructionDtl> implements IDesignConstructionDtlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private FileFeign fileFeign;

    @Override
    public void createOrUpdateConstructionDtls(List<DesignConstructionDtlDTO> data, String dcId, String token) {
        List<DesignConstructionDtlDTO> models = getDesConstDtlListByDcId(dcId);
        List<String> oldRelations = models.stream().map(DesignConstructionDtlDTO::getDcdFile).collect(Collectors.toList());
        QueryWrapper<DesignConstructionDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Dc_Id", dcId);
        baseMapper.delete(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<String> relationIds = new ArrayList<>();
            List<String> fileIds = new ArrayList<>();
            List<DesignConstructionDtl> entities = data.stream().map(d -> {
                DesignConstructionDtl entity = new DesignConstructionDtl();
                BeanUtils.copyProperties(d, entity);
                entity.setId(GenerateUUID.getUUID());
                entity.setDcId(dcId);
                relationIds.add(d.getDcdFile());
                fileIds.add(d.getDcdFileIds());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
            sysFileAnnexService.refreshRelationFile(relationIds, fileIds, dcId);
            oldRelations.removeAll(relationIds);
        }
        if (!CollectionUtils.isEmpty(oldRelations))
            fileFeign.backDelFileByRelations(dcId, token, oldRelations);
    }

    @Override
    public void deleteByDcId(String dcId, String token) {
        List<DesignConstructionDtlDTO> models = getDesConstDtlListByDcId(dcId);
        QueryWrapper<DesignConstructionDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Dc_Id", dcId);
        baseMapper.delete(wrapper);
        List<String> oldRelations = models.stream().map(DesignConstructionDtlDTO::getDcdFile).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(oldRelations))
            fileFeign.backDelFileByRelations(dcId, token, oldRelations);
    }

    @Override
    public List<DesignConstructionDtlDTO> getDesConstDtlListByDcId(String dcId) {
        QueryWrapper<DesignConstructionDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Dc_Id", dcId);
        List<DesignConstructionDtl> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.stream().map(e -> {
                DesignConstructionDtlDTO d = new DesignConstructionDtlDTO();
                BeanUtils.copyProperties(e, d);
                return d;
            }).collect(Collectors.toList());
        return new ArrayList<>();
    }

}
