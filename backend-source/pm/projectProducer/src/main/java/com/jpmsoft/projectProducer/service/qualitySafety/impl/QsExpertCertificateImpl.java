package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Qs.QsExpertCertificateDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsExpertCertificateMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsExpertCertificate;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsExpertCertificateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/27 10:12
 */
@Service
public class QsExpertCertificateImpl extends ServiceImpl<QsExpertCertificateMapper, QsExpertCertificate> implements IQsExpertCertificateService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateQsExpertCertificate(List<QsExpertCertificateDto> datas, String exId) {
        delQsExpertCertificateByExId(exId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<QsExpertCertificate> entitys = datas.stream().map(data -> {
                QsExpertCertificate entity = new QsExpertCertificate();
                BeanUtils.copyProperties(data, entity);
                entity.setId(ValidateUtil.isEmpty(entity.getId()) ? GenerateUUID.getUUID() : entity.getId());
                entity.setExId(exId);
                sysFileAnnexService.refreshRelationFile(data.getEcDoc(), data.getEcDocIds(), entity.getId());
                return entity;
            }).collect(Collectors.toList());
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void delQsExpertCertificateByExId(String exId) {
        QueryWrapper<QsExpertCertificate> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Id", exId);
        baseMapper.delete(wrapper);
    }
}
