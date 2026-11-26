package com.jpmsoft.projectProducer.service.supervision.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.common.model.response.Supervisor.ConSupPlanResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.supervision.ConSupPlanMapper;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupPlan;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.supervision.IConSupPlanService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ConSupPlanImpl extends ServiceImpl<ConSupPlanMapper, ConSupPlan> implements IConSupPlanService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConSupPlan(ConSupPlanRequest request) {
        ConSupPlan entity = new ConSupPlan();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getSpId())) {
            entity.setSpId(GenerateUUID.getUUID());
            entity.setSpCreateTime(new Date());
            entity.setSpStateTime(null);
            entity.setSpState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSpId(), request.gettId(), entity.getSpState() + "", entity.getSpCreateUid(), entity.getSpCreateUname()
                    , entity.getSpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSpFile(), request.getSpImplementFile()), entity.getSpId());

        } else {
            ConSupPlan model = baseMapper.selectById(request.getSpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSpCreateTime(model.getSpCreateTime());
            entity.setSpCreateUid(model.getSpCreateUid());
            entity.setSpCreateUname(model.getSpCreateUname());
            entity.setSpState(model.getSpState());
            entity.setSpStateTime(model.getSpStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSpId(), request.gettId(), entity.getSpState() + "", entity.getSpCreateUid(), entity.getSpCreateUname()
                    , entity.getSpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSpFile(), request.getSpImplementFile()),
                    Arrays.asList(request.getFileIds(), request.getSpFileIds(), request.getSpImplementFileIds()), entity.getSpId());

        }
        return BaseResponse.ok(entity.getSpId());
    }

    @Override
    public BaseResponse getConSupPlanList(ConSupPlanRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Sup_Plan", uId, "Sp_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Sp_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSupPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("spTitle").put("pName").put("conName").put("pId").put("spType");
        IPage<ConSupPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConSupPlanResponse> datas = baseMapper.getConSupPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConSupPlanById(String spId) {
        ConSupPlanResponse data = baseMapper.getConSupPlanById(spId);
        return BaseResponse.ok(data);
    }
}
