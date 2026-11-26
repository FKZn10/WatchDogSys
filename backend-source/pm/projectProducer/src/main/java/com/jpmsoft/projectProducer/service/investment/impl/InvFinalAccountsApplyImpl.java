package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvFinalAccountsApplyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvFinalAccountsApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFinalAccountsApply;
import com.jpmsoft.projectProducer.service.investment.IInvFinalAccountsApplyDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvFinalAccountsApplyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class InvFinalAccountsApplyImpl extends ServiceImpl<InvFinalAccountsApplyMapper, InvFinalAccountsApply>
        implements IInvFinalAccountsApplyService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IInvFinalAccountsApplyDtlService iInvFinalAccountsApplyDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvFinalAccountsApply(InvFinalAccountsApplyRequest request) {
        InvFinalAccountsApply entity = new InvFinalAccountsApply();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getFaId())) {
            entity.setFaCreateTime(new Date());
            entity.setFaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getFaId(), request.gettId(), entity.getFaState() + "", entity.getFaCreateUid(), entity.getFaCreateUname(), entity.getFaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getFaApprovalFile()), entity.getFaId());

        } else {
            InvFinalAccountsApply model = baseMapper.selectById(request.getFaId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getFaState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setFaState(model.getFaState());
            entity.setFaStateTime(model.getFaStateTime());
            entity.setFaCreateTime(model.getFaCreateTime());
            entity.setFaCreateUid(model.getFaCreateUid());
            entity.setFaCreateUname(model.getFaCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getFaId(), request.gettId(), entity.getFaState() + "", entity.getFaCreateUid(), entity.getFaCreateUname(), entity.getFaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getFaApprovalFile()), Arrays.asList(request.getFileIds(), request.getFaApprovalFileIds()), entity.getFaId());

        }
        iInvFinalAccountsApplyDtlService.createOrUpdate(request.getInvFinalAccountsApplyDtls(), entity.getFaId());
        return BaseResponse.ok(entity.getFaId());
    }

    @Override
    public BaseResponse getInvFinalAccountsApplyList(InvFinalAccountsApplyRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Fa_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Fa_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvFinalAccountsApplyRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put(request.getFaApprovedTimes(), "faApprovedTimeStart", "faApprovedTimeEnd");
        IPage<InvFinalAccountsApplyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvFinalAccountsApplyResponse> datas = baseMapper.getInvFinalAccountsApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvFinalAccountsApplyByfaId(String faId) {
        InvFinalAccountsApplyResponse data = baseMapper.getInvFinalAccountsApplyByfaId(faId);
        if (data != null) data.setInvFinalAccountsApplyDtls(iInvFinalAccountsApplyDtlService.getListByFaId(faId));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvFinalAccountsApplyAndDtlByPid(String pId) {
        InvFinalAccountsApplyResponse data = baseMapper.getInvFinalAccountsApplyAndDtlByPid(pId);
        if (data != null)
            data.setInvFinalAccountsApplyDtls(iInvFinalAccountsApplyDtlService.getListByFaId(data.getFaId()));
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvFinalAccountsApplyByfaId(String faId) {
        InvFinalAccountsApply model = baseMapper.selectById(faId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getFaState())) return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(faId);
        iInvFinalAccountsApplyDtlService.delteByFaId(faId);
        baseMapper.deleteById(faId);
        return BaseResponse.ok();
    }

    @Override
    public boolean havingFinalApplyWithPid(String pId) {
        QueryWrapper<InvFinalAccountsApply> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.ne("Fa_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.exists(wrapper)) return true;
        return false;
    }

    @Override
    public BigDecimal selectContractApprovedAmt(String conId) {
        QueryWrapper<InvFinalAccountsApply> wrapper = new QueryWrapper<>();
        wrapper.eq("con_Id", conId);
        wrapper.ne("Fa_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<InvFinalAccountsApply> data=baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
        {
            return data.stream().findFirst().get().getFaApprovedAmt();
        }
        return BigDecimal.ZERO;
    }
}
