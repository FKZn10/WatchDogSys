package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentActualAllocationModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.AgentActualAllocationMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentActualAllocation;
import com.jpmsoft.projectProducer.service.contract.IAgentActualAllocationService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AgentActualAllocationImpl extends
        ServiceImpl<AgentActualAllocationMapper, AgentActualAllocation>
        implements IAgentActualAllocationService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAgentActualAllocation(AgentActualAllocationModel request) {
        AgentActualAllocation entity = new AgentActualAllocation();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getAalId())) {
            entity.setAalId(GenerateUUID.getUUID());
            entity.setAalCreateTime(new Date());
            entity.setAalStateTime(null);
            entity.setAalState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getAalId(), request.gettId(), entity.getAalState() + "", entity.getAalCreateUid(), entity.getAalCreateUname()
                    , entity.getAalCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getAalGuaranteeFile(), request.getAalLiquidatedDamages()), entity.getAalId());

        } else {
            AgentActualAllocation model = baseMapper.selectById(request.getAalId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getAalState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setAalCreateTime(model.getAalCreateTime());
            entity.setAalCreateUid(model.getAalCreateUid());
            entity.setAalCreateUname(model.getAalCreateUname());
            entity.setAalState(model.getAalState());
            entity.setAalStateTime(model.getAalStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getAalId(), request.gettId(), entity.getAalState() + "", entity.getAalCreateUid(), entity.getAalCreateUname()
                    , entity.getAalCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getAalGuaranteeFile(), request.getAalLiquidatedDamages()),
                    Arrays.asList(request.getFileIds(), request.getGuaranteeIds(), request.getLiquidatedIds()), entity.getAalId());
        }
        return BaseResponse.ok(entity.getAalId());
    }

    @Override
    public BaseResponse getAgentActualAllocationList(AgentActualAllocationModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Agent_Actual_Allocation", uId, "Aal_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Aal_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<AgentActualAllocationModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conName").put("pCode")
                .put("aalAgentConCode").put("aalConCode").put("aalContacts").put("aalContactTelephone")
                .put("aalPayType").put("aalAgentConType").put("aalCreateUname").put("aalState");
        IPage<AgentActualAllocationModel> page = new Page<>(request.getPage(), request.getSize());
        List<AgentActualAllocationModel> datas = baseMapper.getAgentActualAllocationList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getAgentActualAllocationById(String aalId) {
        AgentActualAllocationModel data = baseMapper.getAgentActualAllocationById(aalId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getAalThisPayByConId(String conId) {
        BigDecimal money = baseMapper.getAalThisPayByConId(conId);
        return BaseResponse.ok(money == null ? 0 : money);
    }

    @Override
    public BaseResponse getAalThisPayMoneyByAgentConId(String aalAgentConId) {
        BigDecimal money = baseMapper.getAalThisPayMoneyByAgentConId(aalAgentConId);
        return BaseResponse.ok(money == null ? 0 : money);
    }
}
