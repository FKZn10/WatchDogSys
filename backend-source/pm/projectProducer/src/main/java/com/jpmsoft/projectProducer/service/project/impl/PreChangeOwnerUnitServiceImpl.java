package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.PreChangeOwnerUnitDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.PreChangeOwnerUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreChangeOwnerUnit;
import com.jpmsoft.projectProducer.service.project.IPreChangeOwnerUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhelin
 * @date 2025/05/13
 */
@Service
public class PreChangeOwnerUnitServiceImpl extends ServiceImpl<PreChangeOwnerUnitMapper, PreChangeOwnerUnit> implements IPreChangeOwnerUnitService {
    /**
     * 保存变更业主单位
     *
     * @param pprcId
     * @param ownerUnits
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveChangeOwnerUnit(String pprcId, List<PreChangeOwnerUnitDTO> ownerUnits) {
        // 处理旧数据
        LambdaQueryWrapper<PreChangeOwnerUnit> ownerUnitWrapper = new LambdaQueryWrapper<>();
        ownerUnitWrapper.eq(PreChangeOwnerUnit::getPprcId, pprcId);
        List<PreChangeOwnerUnit> oldOwnerUnits = list(ownerUnitWrapper);
        if (!oldOwnerUnits.isEmpty()) {
            remove(ownerUnitWrapper);
        }
        if (CollUtil.isNotEmpty(ownerUnits)) {
            List<PreChangeOwnerUnit> saveOwnerUnits = new ArrayList<>();
            ownerUnits.forEach(ownerUnit -> {
                PreChangeOwnerUnit preOwnerUnit = new PreChangeOwnerUnit();
                BeanUtil.copyProperties(ownerUnit, preOwnerUnit);
                preOwnerUnit.setPprcId(pprcId);
                preOwnerUnit.setId(GenerateUUID.getUUID());
                saveOwnerUnits.add(preOwnerUnit);
            });
            saveBatch(saveOwnerUnits);
        }
    }

    /**
     * 根据pprcId获取变更业主单位详情
     *
     * @param pprcId
     * @return
     */
    @Override
    public List<PreChangeOwnerUnitDTO> getChangeOwnerUnitByPprcId(String pprcId) {
        return baseMapper.getChangeOwnerUnitByPprcId(pprcId);
    }
}
