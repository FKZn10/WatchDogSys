package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecIntentionMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecIntentionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecIntention;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import com.jpmsoft.projectProducer.service.secInfo.ISecIntentionService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProjectService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/20 16:15
 */
@Service
public class SecIntentionImpl extends FileImpl<SecIntentionMapper, SecIntention, SecIntentionDTO> implements ISecIntentionService {

    @Resource
    private ISecInfoService secInfoService;
    @Resource
    private ISecProjectService secProjectService;

    @Override
    @CodeGeneration(key = "id", codeParam = "siCode", method = "setSiCode", codeRule = "SecIntention", beanClass = SecIntentionDTO.class)
    public BaseResponse createOrUpdate(SecIntentionDTO request) {
        SecIntention entity = new SecIntention();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecIntentionById(String id) {
        SecIntention data = baseMapper.selectById(id);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        SecIntentionDTO res = new SecIntentionDTO();
        BeanUtils.copyProperties(data, res);
        if (ValidateUtil.isNotEmpty(data.getSecId())) {
            SecInfo sec = secInfoService.getById(data.getSecId());
            if (ValidateUtil.isNotNull(sec)) {
                res.setSecMoney(sec.getSecMoney());
                res.setSecName(sec.getSecName());
                res.setSecProjects(secProjectService.getSecInfoProject(sec.getSecId()));
            }
        }
        return BaseResponse.ok(res);
    }
}
