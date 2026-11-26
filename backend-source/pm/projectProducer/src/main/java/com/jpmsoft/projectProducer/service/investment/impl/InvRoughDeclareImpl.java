package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvRoughDeclareRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRoughDeclareDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvRoughDeclareResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvRoughDeclareMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRoughDeclare;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.investment.IInvRoughDeclareDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvRoughDeclareService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class InvRoughDeclareImpl extends ServiceImpl<InvRoughDeclareMapper, InvRoughDeclare> implements IInvRoughDeclareService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvRoughDeclareDtlService invRoughDeclareDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvRoughDeclare(InvRoughDeclareRequest request) {
        InvRoughDeclare entity = new InvRoughDeclare();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getRdId())) {
            entity.setRdCreateTime(new Date());
            entity.setRdId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getRdId(), request.gettId(), entity.getRdState() + "", entity.getRdCreateUid(), entity.getRdCreateUname(), entity.getRdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getRdId());
        } else {
            InvRoughDeclare model = baseMapper.selectById(request.getRdId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getRdState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setRdState(model.getRdState());
            entity.setRdStateTime(model.getRdStateTime());
            entity.setRdCreateTime(model.getRdCreateTime());
            entity.setRdCreateUid(model.getRdCreateUid());
            entity.setRdCreateUname(model.getRdCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getRdId(), request.gettId(), entity.getRdState() + "", entity.getRdCreateUid(), entity.getRdCreateUname(), entity.getRdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()), Arrays.asList(request.getFileIds()), entity.getRdId());
        }
        invRoughDeclareDtlService.createOrUpdateInvRoughDeclareDtl(request.getInvRoughDeclareDtls(), entity.getRdId());
        return BaseResponse.ok(entity.getRdId());
    }

    @Override
    public BaseResponse getInvRoughDeclareList(InvRoughDeclareRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Rd_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Rd_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvRoughDeclareRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("RdPreparedByName").put("RdAuditUnitName").put("pName").put("pId").put("RdPreparedBy").put("RdAuditUnit").put("RdSplitType");
        IPage<InvRoughDeclareResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvRoughDeclareResponse> datas = baseMapper.getInvRoughDeclareList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvRoughDeclareByRdId(String RdId) {
        InvRoughDeclareResponse data = baseMapper.getInvRoughDeclareByRdId(RdId);
        if (data != null) {
            List<InvRoughDeclareDtlModel> child = invRoughDeclareDtlService.getInvRoughDeclareDtlModelByRdId(data.getRdId());
            data.setInvRoughDeclareDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvRoughDeclareByRdId(String RdId) {
        InvRoughDeclare model = baseMapper.selectById(RdId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getRdState())) return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(RdId);
        invRoughDeclareDtlService.deleteAllByRdId(RdId);
        baseMapper.deleteById(RdId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getInvRoughDeclareByPId(String pId) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvRoughDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Rd_State", ApprovalStatusEnum.Already_Over.getIndex());
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }

    @Override
    public BaseResponse getInvRoughDeclareAndDtlByPid(String pId) {
        InvRoughDeclareResponse data = baseMapper.getInvRoughDeclareAndDtlByPid(pId);
        if (data != null) {
            List<InvRoughDeclareDtlModel> child = invRoughDeclareDtlService.getInvRoughDeclareDtlModelByRdId(data.getRdId());
            data.setInvRoughDeclareDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvRoughDeclareDtlByPIds(List<String> pIds) {
        if (pIds == null || pIds.size() < 1) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvRoughDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pIds.get(0));
        for (int i = 1; i < pIds.size(); i++) {
            wrapper.or().eq("P_Id", pIds.get(i));
        }
        List<InvRoughDeclare> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<String> pId = new ArrayList<>();
            List<String> bdids = new ArrayList<>();
            datas.forEach(data -> {
                pId.add(data.getPId());
                bdids.add(data.getRdId());
            });
            Map<String, List<InvRoughDeclareDtlModel>> map = invRoughDeclareDtlService.getInvRoughDeclareDtlModelByRdIds(pId, bdids);
            return BaseResponse.ok(map);
        }
        return BaseResponse.ok(null);
    }
}
