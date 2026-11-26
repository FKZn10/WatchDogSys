package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementApplyRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementApplyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementApplyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class InvSettlementApplyImpl extends ServiceImpl<InvSettlementApplyMapper, InvSettlementApply> implements IInvSettlementApplyService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvSettlementApply(InvSettlementApplyRequest request) {
        return createOrUpdateInvSettlementApply(request, true);
    }

    @Override
    public BaseResponse updateInvSettlementApplyOnlyInfo(InvSettlementApplyRequest request) {
        if (ValidateUtil.isEmpty(request.getSaId())) return BaseResponse.fail(ResMsg.notReqParam);
        return createOrUpdateInvSettlementApply(request, false);
    }


    @Override
    public BaseResponse getInvSettlementApplyList(InvSettlementApplyRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Inv_Settlement_Apply", uId, "Sa_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Sa_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvSettlementApplyRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("saCuAf").put("conId").put("saSubmissionUser").put("saSubmissionPhone")
                .put("pName").put("conName").put("saHistorical").put("pId").put("saConCode").put("saExamineUser")
                .put("saExaminePhone").put("haveCostConsult").put("saState").put("saContractorName")
                .put(request.getSaCreateTimes(),"saCreateDateStart","saCreateDateEnd").put("saCreateUname");
        IPage<InvSettlementApplyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvSettlementApplyResponse> datas = baseMapper.getInvSettlementApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvSettlementApplyBysaId(String saId) {
        InvSettlementApplyResponse data = baseMapper.getInvSettlementApplyBysaId(saId);
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvSettlementApplyBysaId(String saId) {
        InvSettlementApply model = baseMapper.selectById(saId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getSaState()))
            return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(saId);
        baseMapper.deleteById(saId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getLastApprovalInvSettlementApplyByConId(String conId) {
        return BaseResponse.ok(baseMapper.getLastApprovalInvSettlementApplyByConId(conId));
    }

    private BaseResponse createOrUpdateInvSettlementApply(InvSettlementApplyRequest request, boolean refershFile) {
        InvSettlementApply entity = new InvSettlementApply();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSaId())) {
            entity.setSaCreateTime(new Date());
            entity.setSaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSaId(), request.gettId(), entity.getSaState() + "", entity.getSaCreateUid(), entity.getSaCreateUname()
                    , entity.getSaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            if (refershFile) {
                sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSaAuditReport(), request.getSaDraft(), request.getSaSupplement(), request.getSaReportFile(), request.getSaReviewReportUnit()), entity.getSaId());
            }
        } else {
            InvSettlementApply model = baseMapper.selectById(request.getSaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
//            if (JudgeUtil.nonModifyState(model.getSaState()))
//                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSaState(model.getSaState());
            entity.setSaStateTime(model.getSaStateTime());
            entity.setSaCreateTime(model.getSaCreateTime());
            entity.setSaCreateUid(model.getSaCreateUid());
            entity.setSaCreateUname(model.getSaCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSaId(), request.gettId(), entity.getSaState() + "", entity.getSaCreateUid(), entity.getSaCreateUname()
                    , entity.getSaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            if (refershFile) {
                sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSaAuditReport(), request.getSaDraft(), request.getSaSupplement(), request.getSaReportFile(), request.getSaReviewReportUnit())
                        , Arrays.asList(request.getFileIds(), request.getSaAuditReportIds(), request.getSaDraftIds(), request.getSaSupplementIds(), request.getSaReportFileIds(), request.getSaReviewReportUnitIds())
                        , entity.getSaId());
            }
        }
        return BaseResponse.ok(entity.getSaId());
    }

}
