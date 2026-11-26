package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecQePreTrialMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQePreTrialDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQePreTrial;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQePreTrialService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:20
 */
@Service
public class SecQePreTrialImpl extends BaseFileImpl<SecQePreTrialMapper, SecQePreTrial, SecQePreTrialDto>
        implements ISecQePreTrialService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    public BaseResponse createOrUpdateSecQePreTrial(SecQePreTrialDto request) {
        SecQePreTrial entity = new SecQePreTrial();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecQePreTrialById(String id) {
        return BaseResponse.ok(baseMapper.getSecQePreTrialById(id));
    }
}
