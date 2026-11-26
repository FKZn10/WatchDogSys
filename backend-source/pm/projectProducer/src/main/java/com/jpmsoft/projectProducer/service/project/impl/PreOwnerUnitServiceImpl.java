package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.PreOwnerUnitDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.PreOwnerUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreOwnerUnit;
import com.jpmsoft.projectProducer.service.project.IPreOwnerUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreOwnerUnitServiceImpl extends ServiceImpl<PreOwnerUnitMapper, PreOwnerUnit> implements IPreOwnerUnitService {

    /**
     * 保存业主单位人员详情
     *
     * @param pprId
     * @param ownerUnits
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOwnerUnit(String pprId, List<PreOwnerUnitDTO> ownerUnits) {
        // 处理旧数据
        LambdaQueryWrapper<PreOwnerUnit> ownerUnitWrapper = new LambdaQueryWrapper<>();
        ownerUnitWrapper.eq(PreOwnerUnit::getPprId, pprId);
        List<PreOwnerUnit> oldOwnerUnits = list(ownerUnitWrapper);
        if (!oldOwnerUnits.isEmpty()) {
            remove(ownerUnitWrapper);
        }
        if (!ownerUnits.isEmpty()) {
            List<PreOwnerUnit> saveOwnerUnits = new ArrayList<>();
            ownerUnits.forEach(ownerUnit -> {
                PreOwnerUnit preOwnerUnit = new PreOwnerUnit();
                BeanUtils.copyProperties(ownerUnit, preOwnerUnit);
                preOwnerUnit.setPprId(pprId);
                preOwnerUnit.setId(GenerateUUID.getUUID());
                saveOwnerUnits.add(preOwnerUnit);
            });
            saveBatch(saveOwnerUnits);
        }
    }

    /**
     * 获取业主单位人员详情列表
     *
     * @param pprId
     * @return
     */
    @Override
    public List<PreOwnerUnitDTO> getPreOwnerUnitByPprId(String pprId) {
        return baseMapper.getPreOwnerUnitByPprId(pprId);
    }
}
