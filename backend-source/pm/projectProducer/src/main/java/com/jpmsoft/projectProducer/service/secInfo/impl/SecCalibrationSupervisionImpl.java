package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.secInfo.SecCalibrationSupervisionMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationSupervisionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationSupervision;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecCalibrationSupervisionVO;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationDtlsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecCalibrationSupervisionService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSupervisionDtlsService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
@Service
public class SecCalibrationSupervisionImpl extends FileImpl<SecCalibrationSupervisionMapper, SecCalibrationSupervision, SecCalibrationSupervisionDTO> implements ISecCalibrationSupervisionService {
    @Resource
    private ISecCalibrationDtlsService secCalibrationDtlsService;

    @Resource
    private ISecSupervisionDtlsService secSupervisionDtlsService;

    @Override
    public BaseResponse createOrUpdateSecCalibrationSupervision(SecCalibrationSupervisionDTO request) {
        SecCalibrationSupervision entity = new SecCalibrationSupervision();
        createOrUpdateFileEntity(entity, request);
        secCalibrationDtlsService.createOrUpdateSecCalibrationDtls(request.getSecCalibrationDtls(), entity.getScsId());
        secSupervisionDtlsService.createOrUpdateSecSupervisionDtls(request.getSecSupervisionDtls(), entity.getScsId());
        return BaseResponse.ok(entity.getScsId());
    }

    @Override
    public BaseResponse getSecCalibrationSupervisionById(String id) {
        SecCalibrationSupervisionVO data = baseMapper.getSecCalibrationSupervisionById(id);
        return BaseResponse.ok(data);
    }
}
