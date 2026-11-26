package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.FundSchemeApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundSchemeApply;
import com.jpmsoft.projectProducer.service.investment.IFundSchemeApplyDtlService;
import com.jpmsoft.projectProducer.service.investment.IFundSchemeApplyService;
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
import java.util.Map;

@Service
public class FundSchemeApplyImpl extends ServiceImpl<FundSchemeApplyMapper, FundSchemeApply> implements IFundSchemeApplyService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IFundSchemeApplyDtlService fundSchemeApplyDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateFundSchemeApply(FundSchemeApplyModel request) {
        FundSchemeApply entity = new FundSchemeApply();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getFsaId())) {
            entity.setFsaId(GenerateUUID.getUUID());
            entity.setFsaCreateTime(new Date());
            entity.setFsaStateTime(null);
            entity.setFsaState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getFsaId(), request.gettId(), entity.getFsaState() + "", entity.getFsaCreateUid(), entity.getFsaCreateUname()
                    , entity.getFsaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getFsaId());
        } else {
            FundSchemeApply model = baseMapper.selectById(request.getFsaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getFsaState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setFsaCreateTime(model.getFsaCreateTime());
            entity.setFsaCreateUid(model.getFsaCreateUid());
            entity.setFsaCreateUname(model.getFsaCreateUname());
            entity.setFsaState(model.getFsaState());
            entity.setFsaStateTime(model.getFsaStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getFsaId(), request.gettId(), entity.getFsaState() + "", entity.getFsaCreateUid(), entity.getFsaCreateUname()
                    , entity.getFsaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getFsaId());

        }
        fundSchemeApplyDtlService.createOrUpdateFundSchemeApplyDtl(request.getSchemeApplyDtlModels(), entity.getFsaId());
        return BaseResponse.ok(entity.getFsaId());

    }

    @Override
    public BaseResponse getFundSchemeApplyList(FundSchemeApplyModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Fund_Scheme_Apply", uId, "Fsa_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Fsa_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<FundSchemeApplyModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pCode")
                .put("fsaApplyDepName").put("fsaCreateUname").put("fsaState");
        IPage<FundSchemeApplyModel> page = new Page<>(request.getPage(), request.getSize());
        List<FundSchemeApplyModel> datas = baseMapper.getFundSchemeApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getFundSchemeApplyById(String fsaId) {
        FundSchemeApplyModel data = baseMapper.getFundSchemeApplyById(fsaId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getFundSchemeApplyAmtSumByPid(String pId) {
        Map<String, BigDecimal> data = baseMapper.getFundSchemeApplyAmtSumByPid(pId);
        if (data == null) {
            data.put("bdAuditAmtCount", BigDecimal.ZERO);
            data.put("fsaCurAppliedCount", BigDecimal.ZERO);
        } else {
            if (!data.containsKey("bdAuditAmtCount"))
                data.put("bdAuditAmtCount", BigDecimal.ZERO);
            if (!data.containsKey("fsaCurAppliedCount"))
                data.put("fsaCurAppliedCount", BigDecimal.ZERO);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getFundSchemeApplyDtlAmtSumByConId(String conId) {
        BigDecimal data = baseMapper.getFundSchemeApplyDtlAmtSumByConId(conId);
        return BaseResponse.ok(data == null ? BigDecimal.ZERO : data);
    }
}
