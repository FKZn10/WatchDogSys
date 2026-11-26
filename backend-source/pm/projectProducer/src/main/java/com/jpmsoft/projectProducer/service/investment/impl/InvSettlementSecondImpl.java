package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementSecondMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvSettlementSecondDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementSecond;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvSettlementSecondVo;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementSecondService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 10:03
 */
@Service
public class InvSettlementSecondImpl extends BaseFileImpl<InvSettlementSecondMapper, InvSettlementSecond, InvSettlementSecondDto>
        implements IInvSettlementSecondService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateInvSettlementSecond(InvSettlementSecondDto request) {
        InvSettlementSecond entity = new InvSettlementSecond();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSsApprovedFile()), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSsApprovedFile()),
                    Arrays.asList(request.getFileIds(), request.getSsApprovedFileIds()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getInvSettlementSecondList(InvSettlementSecondDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Inv_Settlement_Second", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvSettlementSecondDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("conId").put("ssApprovedComName")
                .put("pName").put("conName").put("ssHistory").put("conType").put("saContractorName")
                .put("saSupervisorName").put("saCostConsultName").put("saHistorical");
        IPage<InvSettlementSecondVo> page = new Page<>(request.getPage(), request.getSize());
        List<InvSettlementSecondVo> datas = baseMapper.getInvSettlementSecondList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvSettlementSecondById(String id) {
        return BaseResponse.ok(baseMapper.getInvSettlementSecondById(id));
    }
}
