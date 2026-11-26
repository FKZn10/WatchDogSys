package com.jpmsoft.projectProducer.service.siteform.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.siteform.DocDistributionUnitDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.siteform.DocDistributionUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.siteform.DocDistributionUnit;
import com.jpmsoft.projectProducer.service.siteform.IDocDistributionUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 下发单位表
 */
@Service
public class DocDistributionUnitImpl extends ServiceImpl<DocDistributionUnitMapper, DocDistributionUnit> implements IDocDistributionUnitService {
    /**
     * 批量新增或更新下发单位
     *
     * @param requests
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchCreateOrUpdateDocDistributionUnit(List<DocDistributionUnitDTO> requests) {
        if (ValidateUtil.isEmpty(requests)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String retainUnitType = "发起部门留存";
        List<DocDistributionUnitDTO> retainRequests = requests.stream()
                .filter(dto -> ValidateUtil.isNotEmpty(dto.getUnitType()) && retainUnitType.equalsIgnoreCase(dto.getUnitType()))
                .collect(Collectors.toList());
        List<DocDistributionUnit> retainUnits = this.list(Wrappers.<DocDistributionUnit>lambdaQuery()
                .eq(DocDistributionUnit::getUnitType, retainUnitType)
                .eq(DocDistributionUnit::getMainTableId, retainRequests.get(0).getMainTableId())
        );
        if (ValidateUtil.isNotEmpty(retainRequests)) {
            if (ValidateUtil.isNotEmpty(retainUnits)) {
                for (DocDistributionUnit entity : retainUnits) {
                    if (ValidateUtil.isNotEmpty(entity.getDduId())) {
                        this.update(Wrappers.<DocDistributionUnit>lambdaUpdate()
                                .set(DocDistributionUnit::getIsElectronic, retainRequests.get(0).getIsElectronic())
                                .eq(DocDistributionUnit::getDduId, entity.getDduId()));
                    }
                }
            } else {
                List<DocDistributionUnit> retainEntities = retainRequests.stream().map(dto -> {
                    DocDistributionUnit unit = new DocDistributionUnit();
                    BeanUtils.copyProperties(dto, unit);
                    if (ValidateUtil.isEmpty(unit.getDduId())) {
                        unit.setDduId(GenerateUUID.getUUID());
                    }
                    return unit;
                }).collect(Collectors.toList());
                this.saveBatch(retainEntities);
            }
        }

        List<DocDistributionUnit> entities = requests.stream().map(dto -> {
            if (ValidateUtil.isNotEmpty(dto.getUnitType()) && retainUnitType.equalsIgnoreCase(dto.getUnitType())) {
                return null;
            }
            DocDistributionUnit unit = new DocDistributionUnit();
            BeanUtils.copyProperties(dto, unit);
            if (ValidateUtil.isEmpty(unit.getDduId())) {
                unit.setDduId(GenerateUUID.getUUID());
            }
            return unit;
        }).filter(ValidateUtil::isNotNull).collect(Collectors.toList());
        if (ValidateUtil.isNotEmpty(entities)) {
            this.saveOrUpdateBatch(entities);
        }
        return BaseResponse.ok();
    }
}
