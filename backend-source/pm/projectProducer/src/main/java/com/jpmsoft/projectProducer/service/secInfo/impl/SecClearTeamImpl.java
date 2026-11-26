package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.secInfo.SecClearTeamMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecClearTeamDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecClearTeam;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecClearTeamVO;
import com.jpmsoft.projectProducer.service.secInfo.ISecClearTeamDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecClearTeamService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
@Service
public class SecClearTeamImpl extends FileImpl<SecClearTeamMapper, SecClearTeam, SecClearTeamDTO> implements ISecClearTeamService {
    @Resource
    private ISecClearTeamDtlService secClearTeamDtlService;

    @Override
    public BaseResponse createOrUpdateSecClearTeam(SecClearTeamDTO request) {
        SecClearTeam entity = new SecClearTeam();
        createOrUpdateFileEntity(entity, request);
        secClearTeamDtlService.createOrUpdateSecClearTeamDtl(request.getSecClearTeamDtls(), entity.getSctId());
        return BaseResponse.ok(entity.getSctId());
    }

    @Override
    public BaseResponse getSecClearTeamById(String id) {
        SecClearTeamVO data=baseMapper.getSecClearTeamById(id);
        return BaseResponse.ok(data);
    }
}
