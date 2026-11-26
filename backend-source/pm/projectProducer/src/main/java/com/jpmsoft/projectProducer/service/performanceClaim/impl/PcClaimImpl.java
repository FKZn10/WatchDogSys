package com.jpmsoft.projectProducer.service.performanceClaim.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Claim.PcClaimRequest;
import com.jpmsoft.common.model.response.Claim.PcClaimResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.performanceClaim.PcClaimMapper;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcClaim;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcClaimDtlService;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcClaimService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PcClaimImpl extends ServiceImpl<PcClaimMapper, PcClaim> implements IPcClaimService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IPcClaimDtlService pcClaimDtlService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePcClaim(PcClaimRequest request) {
        PcClaim entity = new PcClaim();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getClaimId())) {
            entity.setClaimId(GenerateUUID.getUUID());
            entity.setClaimCreateTime(new Date());
            entity.setClaimStateTime(null);
            entity.setClaimState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getClaimId(), request.gettId(), entity.getClaimState() + "", entity.getClaimCreateUid(), entity.getClaimCreateUname()
                    , entity.getClaimCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getClaimId());

        } else {
            PcClaim model = baseMapper.selectById(request.getClaimId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getClaimState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setClaimCreateTime(model.getClaimCreateTime());
            entity.setClaimCreateUid(model.getClaimCreateUid());
            entity.setClaimCreateUname(model.getClaimCreateUname());
            entity.setClaimState(model.getClaimState());
            entity.setClaimStateTime(model.getClaimStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getClaimId(), request.gettId(), entity.getClaimState() + "", entity.getClaimCreateUid(), entity.getClaimCreateUname()
                    , entity.getClaimCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getClaimId());

        }
        pcClaimDtlService.createOrUpdatePcClaimDtl(request.getPcClaimDtls(), entity.getClaimId());
        return BaseResponse.ok(entity.getClaimId());
    }

    @Override
    public BaseResponse getPcClaimList(PcClaimRequest request, String uId) {
        if (ValidateUtil.isEmpty(request.getClaimType()))
            return BaseResponse.fail(ResMsg.notReqParam);
        String authSql;
        if (request.getClaimType() == 1) //索赔
            authSql = sysUserService.getAuthSql("Pc_Claim", uId, "Claim_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Claim_State");
        else  //反索赔
            authSql = sysUserService.getAuthSql("Pc_Claim", uId, "Claim_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Claim_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<PcClaimRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("pId")
                .put("claimType").put("claimFp").put("claimSp");
        IPage<PcClaimResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PcClaimResponse> datas = baseMapper.getPcClaimList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getPcClaimById(String claimId) {
        PcClaimResponse data = baseMapper.getPcClaimById(claimId);
        return BaseResponse.ok(data);
    }
}
