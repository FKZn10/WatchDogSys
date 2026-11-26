package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskTemplateRelationMapper;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskTemplateRelationDTO;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskTemplateRelation;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskTemplateRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 13:45
 */
@Service
public class RiskTemplateRelationImpl extends ServiceImpl<RiskTemplateRelationMapper, RiskTemplateRelation>
        implements IRiskTemplateRelationService {

    @Override
    public boolean havingPreventionMeasure(String rpmId) {
        QueryWrapper<RiskTemplateRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("Rpm_Id", rpmId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public BaseResponse createOrUpdate(RiskTemplateRelationDTO request) {
        RiskTemplateRelation entity = new RiskTemplateRelation();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            RiskTemplateRelation old = baseMapper.selectById(request.getId());
            if (old == null) return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateUname(old.getCreateUname());
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateTime(old.getCreateTime());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse createOrUpdateBath(List<RiskTemplateRelationDTO> request,String teId) {
        List<String> tIds = request.stream().map(RiskTemplateRelationDTO::getTId).distinct().collect(Collectors.toList());
        QueryWrapper<RiskTemplateRelation> wrapper = new QueryWrapper<>();
        if (tIds.size() > 1)
            wrapper.in("T_ID", tIds);
        else
            wrapper.eq("T_Id", tIds.get(0));
        baseMapper.delete(wrapper);
        List<RiskTemplateRelation> entities = new ArrayList<>();
        for (RiskTemplateRelationDTO dto : request) {
            RiskTemplateRelation entity = new RiskTemplateRelation();
            // BeanUtils.copyProperties(dto, entities);
            entity.setCreateUid(dto.getCreateUid());
            entity.setCreateUname(dto.getCreateUname());
            entity.setRpmId(dto.getRpmId());
            entity.setRtrFieldCode(dto.getRtrFieldCode());
            entity.setTId(dto.getTId());
            entity.setRtrFieldName(dto.getRtrFieldName());
            entity.setId(GenerateUUID.getUUID());
            entity.setTeId(teId);
            entities.add(entity);
        }
        if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse queryList(RiskTemplateRelationDTO request, String uId) {
        QueryMap<RiskTemplateRelationDTO> queryMap = new QueryMap<>(request);
        queryMap.put("rpmId").put("rtrFieldName").put("rtrFieldCode").put("tName").put("tCode");
        IPage<RiskTemplateRelationDTO> page = new Page<>(request.getPage(), request.getSize());
        List<RiskTemplateRelationDTO> data = baseMapper.queryList(page, queryMap.getQuery());
        return BaseResponse.ok(data, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse deleteById(String id) {
        RiskTemplateRelation entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }


    private List<RiskTemplateRelation> getTmpRelationByTid(List<String> tids) {
        if (CollectionUtils.isEmpty(tids)) return null;
        QueryWrapper<RiskTemplateRelation> wrapper = new QueryWrapper<>();
        if (tids.size() > 1)
            wrapper.in("T_ID", tids);
        else
            wrapper.eq("T_Id", tids.get(0));
        return baseMapper.selectList(wrapper);
    }
}
