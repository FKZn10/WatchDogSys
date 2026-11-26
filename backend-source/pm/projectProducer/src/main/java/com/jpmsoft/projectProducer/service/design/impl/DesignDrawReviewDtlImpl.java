package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignDrawReviewDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDrawReviewDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawReviewDtl;
import com.jpmsoft.projectProducer.service.design.IDesignDrawReviewDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 11:32
 */
@Service
public class DesignDrawReviewDtlImpl extends ServiceImpl<DesignDrawReviewDtlMapper, DesignDrawReviewDtl> implements IDesignDrawReviewDtlService {

    @Resource
    private FileFeign fileFeign;

    @Override
    public void createOrUpdate(List<DesignDrawReviewDtlDTO> data, String drId, String token) {
        List<DesignDrawReviewDtl> olds = getByDrId(drId);
        if (!CollectionUtils.isEmpty(data)) {
            //删除的数据
            List<String> ids = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getId())).map(DesignDrawReviewDtlDTO::getId).collect(Collectors.toList());
            List<DesignDrawReviewDtl> dels = olds.stream().filter(f -> !ids.contains(f.getId())).collect(Collectors.toList());
            delete(dels, drId, token);
            //新增修改的数据
            QueryWrapper<DesignDrawReviewDtl> wrapper = new QueryWrapper<>();
            wrapper.eq("Dr_Id", drId);
            baseMapper.delete(wrapper);
            List<RefershFileDto> files = new ArrayList<>();
            List<DesignDrawReviewDtl> entities = data.stream().map(d -> {
                DesignDrawReviewDtl entity = new DesignDrawReviewDtl();
                BeanUtils.copyProperties(d, entity);
                entity.setId(GenerateUUID.getUUID());
                entity.setDrId(drId);
                RefershFileDto file = new RefershFileDto();
                file.setEntityId(drId);
                file.setFileIds(Arrays.asList(d.getDrdReplyFileIds(), d.getDrdReviewFileIds()));
                file.setRelationIds(Arrays.asList(d.getDrdReplyFile(), d.getDrdReviewFile()));
                files.add(file);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
            fileFeign.refreshRelationFileBath(files);
        } else
            delete(olds, drId, token);
    }

    private void delete(List<DesignDrawReviewDtl> olds, String drId, String token) {
        if (CollectionUtils.isEmpty(olds)) return;
        List<String> relationId = olds.stream().map(DesignDrawReviewDtl::getDrdReplyFile).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(relationId)) relationId = new ArrayList<>();
        relationId.addAll(olds.stream().map(DesignDrawReviewDtl::getDrdReviewFile).collect(Collectors.toList()));
        fileFeign.backDelFileByRelations(drId, token, relationId);
        baseMapper.deleteBatchIds(olds.stream().map(DesignDrawReviewDtl::getId).collect(Collectors.toList()));
    }

    private List<DesignDrawReviewDtl> getByDrId(String drId) {
        QueryWrapper<DesignDrawReviewDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Dr_Id", drId);
        return baseMapper.selectList(wrapper);
    }
}
