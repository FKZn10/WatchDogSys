package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecExpertCertificateDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertCertificateMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCertificate;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCertificateService;
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
public class SecExpertCertificateImpl extends ServiceImpl<SecExpertCertificateMapper, SecExpertCertificate> implements ISecExpertCertificateService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateSecExpertCertificate(List<SecExpertCertificateDto> datas, String exId) {
        delSecExpertCertificateByExId(exId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecExpertCertificate> entitys = datas.stream().map(data -> {
                SecExpertCertificate entity = new SecExpertCertificate();
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
        QueryWrapper<SecExpertCertificate> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Id", exId);
        baseMapper.delete(wrapper);
    }
}
