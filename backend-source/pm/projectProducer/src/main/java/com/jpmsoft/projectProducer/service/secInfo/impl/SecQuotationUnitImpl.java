package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecQuotationUnitMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQuotationUnitDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQuotationUnit;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQuotationUnitVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQuotationUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:57
 */
@Service
public class SecQuotationUnitImpl extends ServiceImpl<SecQuotationUnitMapper, SecQuotationUnit> implements ISecQuotationUnitService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateSecQuotationUnit(List<SecQuotationUnitDto> datas, String sirId) {
        delSecQuotationUnitBySirId(sirId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecQuotationUnit> entitys = datas.stream().map(data -> {
                SecQuotationUnit entity = new SecQuotationUnit();
                BeanUtils.copyProperties(data, entity);
                entity.setSirId(sirId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                sysFileAnnexService.refreshRelationFile(data.getSquFile(), data.getSquFileIds(), entity.getId());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delSecQuotationUnitBySirId(String sirId) {
        QueryWrapper<SecQuotationUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("Sir_Id", sirId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getSecQuotationUnitBySecId(String secId) {
        List<SecQuotationUnitVo> datas = baseMapper.getSecQuotationUnitBySecId(secId);
        return BaseResponse.ok(datas);
    }
}
