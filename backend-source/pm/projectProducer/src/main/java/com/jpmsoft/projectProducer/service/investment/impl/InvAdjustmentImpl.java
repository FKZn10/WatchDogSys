package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvAdjustmentResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvAdjustmentMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvRelatedRelationshipsMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoBudgetMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustment;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
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
public class InvAdjustmentImpl extends ServiceImpl<InvAdjustmentMapper, InvAdjustment> implements IInvAdjustmentService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvAdjustmentDtlService invAdjustmentDtlService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IConContractService conContractService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private InvRelatedRelationshipsMapper invRelatedRelationshipsMapper;

    @Resource
    private SecInfoBudgetMapper secInfoBudgetMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvAdjustment(InvAdjustmentRequest request) {
        InvAdjustment entity = new InvAdjustment();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getIaId())) {
            entity.setCreateTime(new Date());
            entity.setIaId(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            BigDecimal total=request.getInvAdjustmentDtls().stream().filter(k->ValidateUtil.isNotNull(k.getAdApplyAmount())).map(InvAdjustmentDtlModel::getAdApplyAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
            entity.setTotalAmount(total);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIaId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getIaId());
        } else {
            InvAdjustment model = baseMapper.selectById(request.getIaId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            BigDecimal total=request.getInvAdjustmentDtls().stream().filter(k->ValidateUtil.isNotNull(k.getAdApplyAmount())).map(InvAdjustmentDtlModel::getAdApplyAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
            entity.setTotalAmount(total);
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIaId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()), Arrays.asList(request.getFileIds()), entity.getIaId());
        }
        invAdjustmentDtlService.createOrUpdateInvAdjustmentDtl(request.getInvAdjustmentDtls(), entity.getIaId());
        return BaseResponse.ok(entity.getIaId());
    }

    @Override
    public BaseResponse getInvAdjustmentList(InvAdjustmentRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvAdjustmentRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName");
        IPage<InvAdjustmentResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvAdjustmentResponse> datas = baseMapper.getAdjustmentList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvAdjustmentByIaId(String iaId) {
        InvAdjustmentResponse data = baseMapper.getAdjustmentByIaId(iaId);
        if (data != null) {
            List<InvAdjustmentDtlModel> child = invAdjustmentDtlService.getInvAdjustmentDtlModelByIaId(data.getIaId());
            data.setInvAdjustmentDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvAdjustmentByIaId(String iaId) {
        InvAdjustment model = baseMapper.selectById(iaId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getState())) return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(iaId);
        invAdjustmentDtlService.deleteAllByIaId(iaId);
        baseMapper.deleteById(iaId);
        return BaseResponse.ok();
    }
}
