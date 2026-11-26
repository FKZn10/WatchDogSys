package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.AgentFundAdvanceMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvance;
import com.jpmsoft.projectProducer.service.contract.IAgentFundAdvanceListService;
import com.jpmsoft.projectProducer.service.contract.IAgentFundAdvanceService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AgentFundAdvanceImpl extends ServiceImpl<AgentFundAdvanceMapper, AgentFundAdvance> implements IAgentFundAdvanceService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IAgentFundAdvanceListService agentFundAdvanceListService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAgentFundAdvance(AgentFundAdvanceModel request) {
        AgentFundAdvance entity = new AgentFundAdvance();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getAfaId())) {
            entity.setAfaId(GenerateUUID.getUUID());
            entity.setAfaCreateTime(new Date());
            entity.setAfaStateTime(null);
            entity.setAfaState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getAfaId(), request.gettId(), entity.getAfaState() + "", entity.getAfaCreateUid(), entity.getAfaCreateUname()
                    , entity.getAfaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getAfaId());

        } else {
            AgentFundAdvance model = baseMapper.selectById(request.getAfaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getAfaState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setAfaCreateTime(model.getAfaCreateTime());
            entity.setAfaCreateUid(model.getAfaCreateUid());
            entity.setAfaCreateUname(model.getAfaCreateUname());
            entity.setAfaState(model.getAfaState());
            entity.setAfaStateTime(model.getAfaStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getAfaId(), request.gettId(), entity.getAfaState() + "", entity.getAfaCreateUid(), entity.getAfaCreateUname()
                    , entity.getAfaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getAfaId());

        }
        agentFundAdvanceListService.createOrUpdateAgentFundAdvanceList(request.getFundAdvanceLists(), entity.getAfaId());
        return BaseResponse.ok(entity.getAfaId());
    }

    @Override
    public BaseResponse getAgentFundAdvanceList(AgentFundAdvanceModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Agent_Fund_Advance", uId, "Afa_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Afa_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<AgentFundAdvanceModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conName").put("pCode")
                .put("conContractCode").put("afaApplicationBatch").put("afaCreateUname").put("afaState").put("applyCName");
        IPage<AgentFundAdvanceModel> page = new Page<>(request.getPage(), request.getSize());
        List<AgentFundAdvanceModel> datas = baseMapper.getAgentFundAdvanceList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getAgentFundAdvanceById(String afaId) {
        AgentFundAdvanceModel data = baseMapper.getAgentFundAdvanceById(afaId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getAfaThisMoneyByConId(String conId) {
        BigDecimal money = baseMapper.getAfaThisMoneyByConId(conId);
        return BaseResponse.ok(money == null ? 0 : money);
    }

    @Override
    public List<AgentFundAdvance> getFundAdvanceNotConPayApply(String teId) {
        return baseMapper.getFundAdvanceNotConPayApply(teId);
    }
}
