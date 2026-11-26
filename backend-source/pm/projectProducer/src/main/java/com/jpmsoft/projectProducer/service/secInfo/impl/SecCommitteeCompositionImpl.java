package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.secInfo.SecCommitteeCompositionMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCommitteeCompositionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCommitteeComposition;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecCommitteeCompositionVO;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationCommitteeService;
import com.jpmsoft.projectProducer.service.secInfo.ISecCommitteeCompositionService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSupervisionCommitteeService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
@Service
public class SecCommitteeCompositionImpl extends FileImpl<SecCommitteeCompositionMapper, SecCommitteeComposition, SecCommitteeCompositionDTO> implements ISecCommitteeCompositionService {
    @Resource
    private ISecCalibrationCommitteeService secCalibrationCommitteeService;

    @Resource
    private ISecSupervisionCommitteeService secSupervisionCommitteeService;

    @Override
    public BaseResponse createOrUpdateSecCommitteeComposition(SecCommitteeCompositionDTO request) {
        SecCommitteeComposition entity = new SecCommitteeComposition();
        createOrUpdateFileEntity(entity, request);
        secCalibrationCommitteeService.createOrUpdateSecCalibrationCommittee(request.getSecCalibrationCommittees(), entity.getSccId());
        secSupervisionCommitteeService.createOrUpdateSecSupervisionCommittee(request.getSecSupervisionCommittees(), entity.getSccId());
        return BaseResponse.ok(entity.getSccId());
    }

    @Override
    public BaseResponse getSecCommitteeCompositionById(String id) {
        SecCommitteeCompositionVO data = baseMapper.getSecCommitteeCompositionById(id);
        return BaseResponse.ok(data);
    }
}
