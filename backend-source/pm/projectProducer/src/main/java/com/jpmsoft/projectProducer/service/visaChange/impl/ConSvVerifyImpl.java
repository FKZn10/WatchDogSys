package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConSvVerifyRequest;
import com.jpmsoft.common.model.response.DesignChange.ConSvVerifyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConSvVerifyMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvVerify;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConSvVerifyService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConSvVerifyImpl extends ServiceImpl<ConSvVerifyMapper, ConSvVerify> implements IConSvVerifyService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "svvId", codeParam = "svvCode", method = "setSvvCode", codeRule = "ConSvVerify", beanClass = ConSvVerifyRequest.class)
    public BaseResponse createOrUpdateConSvVerify(ConSvVerifyRequest request) {
        ConSvVerify entity = new ConSvVerify();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSvvCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<ConSvVerify> wrapper = new QueryWrapper<>();
        wrapper.eq("Svis_Id", request.getSvisId());
        wrapper.ne("Svv_State", ApprovalStatusEnum.Invalid.getIndex());
        if (!ValidateUtil.isEmpty(entity.getSvvId()))
            wrapper.ne("Svv_Id", request.getSvvId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.recordExist);

        if (ValidateUtil.isEmpty(request.getSvvId())) {
            entity.setSvvId(GenerateUUID.getUUID());
            entity.setSvvCreateTime(new Date());
            entity.setSvvStateTime(null);
            entity.setSvvState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSvvId(), request.gettId(), entity.getSvvState() + "", entity.getSvvCreateUid(), entity.getSvvCreateUname()
                    , entity.getSvvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getSvvId());

        } else {
            ConSvVerify model = baseMapper.selectById(request.getSvvId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSvvState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSvvCreateTime(model.getSvvCreateTime());
            entity.setSvvCreateUid(model.getSvvCreateUid());
            entity.setSvvCreateUname(model.getSvvCreateUname());
            entity.setSvvState(model.getSvvState());
            entity.setSvvStateTime(model.getSvvStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSvvId(), request.gettId(), entity.getSvvState() + "", entity.getSvvCreateUid(), entity.getSvvCreateUname()
                    , entity.getSvvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getSvvId());

        }
        return BaseResponse.ok(entity.getSvvId());
    }

    @Override
    public BaseResponse getConSvVerifyList(ConSvVerifyRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Sv_Verify", uId, "Svv_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Svv_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSvVerifyRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("svvCode").put("svvProjectName").put("pName")
                .put("conName").put("svvContractorId").put("pId").put("svvState");
        List<ConSvVerifyResponse> data = baseMapper.getConSvVerifyList(params.getQuery());
        Integer total = baseMapper.getConSvVerifyListCount(params.getQuery());
        return BaseResponse.ok(data, total);
    }

    @Override
    public BaseResponse getConSvVerifyById(String svvId) {
        ConSvVerifyResponse data = baseMapper.getConSvVerifyById(svvId);
        return BaseResponse.ok(data);
    }
}
