package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProcurementApplyMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementApplyDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementApply;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementApplyService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:19
 */
@Service
public class SecProcurementApplyImpl extends BaseFileImpl<SecProcurementApplyMapper, SecProcurementApply, SecProcurementApplyDto>
        implements ISecProcurementApplyService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateSecProcurementApply(SecProcurementApplyDto request) {
        SecProcurementApply entity = new SecProcurementApply();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecProcurementApplyById(String id) {
        return BaseResponse.ok(baseMapper.getSecProcurementApplyById(id));
    }
}
