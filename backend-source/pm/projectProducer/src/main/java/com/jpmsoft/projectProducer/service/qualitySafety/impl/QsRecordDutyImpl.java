package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsRecordDutyMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordDutyBathDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecordDuty;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordDutyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/20 13:42
 */
@Service
public class QsRecordDutyImpl extends ServiceImpl<QsRecordDutyMapper, QsRecordDuty> implements IQsRecordDutyService {

    @Override
    public void createOrUpdate(List<QsRecordDutyBathDTO> data) {
        if (!CollectionUtils.isEmpty(data)) {
            List<String> qsrIds = data.stream().map(QsRecordDutyBathDTO::getQsrId).collect(Collectors.toList());
            deleteByQsrIds(qsrIds);
            List<QsRecordDuty> entities = new ArrayList<>();
            for (QsRecordDutyBathDTO d : data) {
                if (!CollectionUtils.isEmpty(d.getQsRecordDutys())) {
                    for (QsRecordDutyDTO e : d.getQsRecordDutys()) {
                        QsRecordDuty entity = new QsRecordDuty();
                        BeanUtils.copyProperties(e, entity);
                        entity.setQsrId(d.getQsrId());
                        entity.setId(GenerateUUID.getUUID());
                        entities.add(entity);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(entities))
                saveBatch(entities);
        }
    }

    @Override
    public void deleteByQsrIds(List<String> ids) {
        QueryWrapper<QsRecordDuty> wrapper = new QueryWrapper<>();
        wrapper.in("Qsr_Id", ids);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<QsRecordDutyDTO> getQsRecordDutyByQsrIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return new ArrayList<>();
        return baseMapper.getQsRecordDutyByQsrIds(ids);
    }


    @Override
    public QsRecordDuty getQsRecordQsrId(String qsrId) {
        LambdaQueryWrapper<QsRecordDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QsRecordDuty::getQsrId, qsrId);
        List<QsRecordDuty> data = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(data)) return data.stream().findFirst().get();
        return null;
    }
}
