package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvEstimateDeclareRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvEstimateDeclareDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvEstimateDeclareResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvEstimateDeclareMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvEstimateDeclare;
import com.jpmsoft.projectProducer.service.investment.IInvEstimateDeclareDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvEstimateDeclareService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class InvEstimateDeclareImpl extends ServiceImpl<InvEstimateDeclareMapper, InvEstimateDeclare> implements IInvEstimateDeclareService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvEstimateDeclareDtlService invEstimateDeclareDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvEstimateDeclare(InvEstimateDeclareRequest request) {
        InvEstimateDeclare entity = new InvEstimateDeclare();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getEdId())) {
            entity.setEdCreateTime(new Date());
            entity.setEdId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEdId(), request.gettId(), entity.getEdState() + "", entity.getEdCreateUid(), entity.getEdCreateUname(), entity.getEdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getEdId());
        } else {
            InvEstimateDeclare model = baseMapper.selectById(request.getEdId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEdState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEdState(model.getEdState());
            entity.setEdStateTime(model.getEdStateTime());
            entity.setEdCreateTime(model.getEdCreateTime());
            entity.setEdCreateUid(model.getEdCreateUid());
            entity.setEdCreateUname(model.getEdCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEdId(), request.gettId(), entity.getEdState() + "", entity.getEdCreateUid(), entity.getEdCreateUname(), entity.getEdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()), Arrays.asList(request.getFileIds()), entity.getEdId());
        }
        invEstimateDeclareDtlService.createOrUpdateInvEstimateDeclareDtl(request.getInvEstimateDeclareDtls(), entity.getEdId());
        return BaseResponse.ok(entity.getEdId());
    }

    @Override
    public BaseResponse getInvEstimateDeclareList(InvEstimateDeclareRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Ed_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ed_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvEstimateDeclareRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("EdPreparedByName").put("EdAuditUnitName").put("pName").put("pId").put("EdPreparedBy").put("EdAuditUnit").put("EdSplitType");
        IPage<InvEstimateDeclareResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvEstimateDeclareResponse> datas = baseMapper.getInvEstimateDeclareList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvEstimateDeclareByEdId(String EdId) {
        InvEstimateDeclareResponse data = baseMapper.getInvEstimateDeclareByEdId(EdId);
        if (data != null) {
            List<InvEstimateDeclareDtlModel> child = invEstimateDeclareDtlService.getInvEstimateDeclareDtlModelByEdId(data.getEdId());
            data.setInvEstimateDeclareDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvEstimateDeclareByEdId(String EdId) {
        InvEstimateDeclare model = baseMapper.selectById(EdId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getEdState())) return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(EdId);
        invEstimateDeclareDtlService.deleteAllByEdId(EdId);
        baseMapper.deleteById(EdId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getInvEstimateDeclareByPId(String pId) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvEstimateDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Ed_State", ApprovalStatusEnum.Already_Over.getIndex());
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }

    @Override
    public BaseResponse getInvEstimateDeclareAndDtlByPid(String pId) {
        InvEstimateDeclareResponse data = baseMapper.getInvEstimateDeclareAndDtlByPid(pId);
        if (data != null) {
            List<InvEstimateDeclareDtlModel> child = invEstimateDeclareDtlService.getInvEstimateDeclareDtlModelByEdId(data.getEdId());
            data.setInvEstimateDeclareDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvEstimateDeclareDtlByPIds(List<String> pIds) {
        if (pIds == null || pIds.size() < 1) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvEstimateDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pIds.get(0));
        for (int i = 1; i < pIds.size(); i++) {
            wrapper.or().eq("P_Id", pIds.get(i));
        }
        List<InvEstimateDeclare> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<String> pId = new ArrayList<>();
            List<String> bdids = new ArrayList<>();
            datas.forEach(data -> {
                pId.add(data.getPId());
                bdids.add(data.getEdId());
            });
            Map<String, List<InvEstimateDeclareDtlModel>> map = invEstimateDeclareDtlService.getInvEstimateDeclareDtlModelByEdIds(pId, bdids);
            return BaseResponse.ok(map);
        }
        return BaseResponse.ok(null);
    }
}
