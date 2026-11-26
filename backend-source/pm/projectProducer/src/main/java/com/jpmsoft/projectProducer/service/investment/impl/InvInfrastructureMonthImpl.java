package com.jpmsoft.projectProducer.service.investment.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvInfrastructureMonthMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureMonthDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonth;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvInfrastructureMonthVo;
import com.jpmsoft.projectProducer.service.investment.IInvInfrastructureMonthDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvInfrastructureMonthService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:08
 */
@Service
public class InvInfrastructureMonthImpl extends BaseFileImpl<InvInfrastructureMonthMapper, InvInfrastructureMonth, InvInfrastructureMonthDto> implements IInvInfrastructureMonthService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IInvInfrastructureMonthDtlService invInfrastructureMonthDtlService;

    @Override
    public BaseResponse createOrUpdateInvInfrastructureMonth(InvInfrastructureMonthDto request) {
        InvInfrastructureMonth im = new InvInfrastructureMonth();
        im = createOrUpdateEntity(im, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), im.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), im.getId());
        }
        invInfrastructureMonthDtlService.createOrUpdateInfrastructureMonthDtl(request.getInvInfrastructureMonthDtls(), im);
        return BaseResponse.ok(im.getId());
    }

    @Override
    public BaseResponse getInvInfrastructureMonthById(String id) {
        InvInfrastructureMonthVo data = baseMapper.getInvInfrastructureMonthById(id);
        return BaseResponse.ok(data);
    }

}
