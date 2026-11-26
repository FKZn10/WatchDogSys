package com.jpmsoft.projectProducer.service.acpm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.acpm.AcpmDJTBSubjectTenderMapper;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmDJTBSubjectTenderDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmDJTBSubjectTender;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmDJTBSubjectTenderVo;
import com.jpmsoft.projectProducer.service.acpm.IAcpmDJTBSubjectTenderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:59
 */
@Service
public class AcpmDJTBSubjectTenderImpl extends ServiceImpl<AcpmDJTBSubjectTenderMapper, AcpmDJTBSubjectTender> implements IAcpmDJTBSubjectTenderService {

    @Override
    public void createOrUpdate(List<AcpmDJTBSubjectTenderDto> subjectTenders, String daId) {
        deleteByDaId(daId);
        if (ValidateUtil.isNotEmpty(subjectTenders)) {
            List<AcpmDJTBSubjectTender> entitys = new ArrayList<>();
            entitys = subjectTenders.stream().map(n -> {
                AcpmDJTBSubjectTender entity = new AcpmDJTBSubjectTender();
                BeanUtils.copyProperties(n, entity);
                entity.setDaId(daId);
                entity.setDasId(ValidateUtil.isEmpty(n.getDasId()) ? GenerateUUID.getUUID() : n.getDasId());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void deleteByDaId(String daId) {
        QueryWrapper<AcpmDJTBSubjectTender> wrapper = new QueryWrapper<>();
        wrapper.eq("Da_Id", daId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<AcpmDJTBSubjectTenderVo> getByDaId(String daId) {
        if (ValidateUtil.isEmpty(daId)) return new ArrayList<>();
        return baseMapper.getByDaId(daId);
    }

    @Override
    public List<AcpmDJTBSubjectTenderVo> getBathByDaIds(List<String> daIds) {
        if (ValidateUtil.isEmpty(daIds)) return new ArrayList<>();
        return baseMapper.getBathByDaIds(daIds);
    }
}
