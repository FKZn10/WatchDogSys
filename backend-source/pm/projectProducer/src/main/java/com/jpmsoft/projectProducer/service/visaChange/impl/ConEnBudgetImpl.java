package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConEnBudgetRequest;
import com.jpmsoft.common.model.response.DesignChange.ConEnBudgetResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConEnBudgetMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnBudget;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConEnBudgetService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ConEnBudgetImpl extends ServiceImpl<ConEnBudgetMapper, ConEnBudget> implements IConEnBudgetService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConEnBudget(ConEnBudgetRequest request) {
        ConEnBudget entity = new ConEnBudget();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getEnbId())) {
            entity.setEnbId(GenerateUUID.getUUID());
            entity.setEnbCreateTime(new Date());
            entity.setEnbStateTime(null);
            entity.setEnbState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEnbId(), request.gettId(), entity.getEnbState() + "", entity.getEnbCreateUid(), entity.getEnbCreateUname()
                    , entity.getEnbCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getEnbConstructionPlan()), entity.getEnbId());

        } else {
            ConEnBudget model = baseMapper.selectById(request.getEnbId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEnbState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEnbCreateTime(model.getEnbCreateTime());
            entity.setEnbCreateUid(model.getEnbCreateUid());
            entity.setEnbCreateUname(model.getEnbCreateUname());
            entity.setEnbState(model.getEnbState());
            entity.setEnbStateTime(model.getEnbStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEnbId(), request.gettId(), entity.getEnbState() + "", entity.getEnbCreateUid(), entity.getEnbCreateUname()
                    , entity.getEnbCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getEnbConstructionPlan()),
                    Arrays.asList(request.getFileIds(), request.getEnbConstructionPlanIds()), entity.getEnbId());

        }
        return BaseResponse.ok(entity.getEnbId());

    }

    @Override
    public BaseResponse getConEnBudgetList(ConEnBudgetRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_En_Budget", uId, "Enb_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Enb_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEnBudgetRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("enisCode").put("enisName")
                .put("enbContractorId").put("pId").put("enbState").put("enisProjectName").put("enisSubCode");
        List<ConEnBudgetResponse> data = baseMapper.getConEnBudgetList(params.getQuery());
        Integer total = baseMapper.getConEnBudgetListCount(params.getQuery());
        return BaseResponse.ok(data, total);
    }

    @Override
    public BaseResponse getConEnBudgetById(String enbId) {
        ConEnBudgetResponse data = baseMapper.getConEnBudgetById(enbId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getNotContainAmtByConId(String conId) {
        return BaseResponse.ok(baseMapper.getNotContainAmtByConId(conId));
    }
}
