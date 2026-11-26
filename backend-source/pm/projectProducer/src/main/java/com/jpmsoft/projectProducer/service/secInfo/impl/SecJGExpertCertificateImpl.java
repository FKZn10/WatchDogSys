package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecJGExpertCertificateDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGExpertCertificateMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCertificate;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertCertificate;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertCertificateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecJGExpertCertificateImpl extends ServiceImpl<SecJGExpertCertificateMapper, SecJGExpertCertificate> implements ISecJGExpertCertificateService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateSecExpertCertificate(List<SecJGExpertCertificateDto> datas, String exId) {
        delSecExpertCertificateByExId(exId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecJGExpertCertificate> entitys = datas.stream().map(data -> {
                SecJGExpertCertificate entity = new SecJGExpertCertificate();
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
    public void delSecExpertCertificateByExId(String exId) {
        QueryWrapper<SecJGExpertCertificate> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Id", exId);
        baseMapper.delete(wrapper);
    }


}
