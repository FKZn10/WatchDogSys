package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConEnVerifyRequest;
import com.jpmsoft.common.model.response.DesignChange.ConEnVerifyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConEnVerifyMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnVerify;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConEnVerifyService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConEnVerifyImpl extends ServiceImpl<ConEnVerifyMapper, ConEnVerify> implements IConEnVerifyService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "envId", codeParam = "envCode", method = "setEnvCode", codeRule = "ConEnVerify", beanClass = ConEnVerifyRequest.class)
    public BaseResponse createOrUpdateConEnVerify(ConEnVerifyRequest request) {
        ConEnVerify entity = new ConEnVerify();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getEnvCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<ConEnVerify> wrapper = new QueryWrapper<>();
        wrapper.eq("Env_Code", request.getEnvCode());
        if (!ValidateUtil.isEmpty(entity.getEnvId()))
            wrapper.ne("Env_Id", request.getEnvId());
        wrapper.ne("Env_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getEnvId())) {
            entity.setEnvId(GenerateUUID.getUUID());
            entity.setEnvCreateTime(new Date());
            entity.setEnvStateTime(null);
            entity.setEnvState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEnvId(), request.gettId(), entity.getEnvState() + "", entity.getEnvCreateUid(), entity.getEnvCreateUname()
                    , entity.getEnvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getEnvId());

        } else {
            ConEnVerify model = baseMapper.selectById(request.getEnvId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEnvState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEnvCreateTime(model.getEnvCreateTime());
            entity.setEnvCreateUid(model.getEnvCreateUid());
            entity.setEnvCreateUname(model.getEnvCreateUname());
            entity.setEnvState(model.getEnvState());
            entity.setEnvStateTime(model.getEnvStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEnvId(), request.gettId(), entity.getEnvState() + "", entity.getEnvCreateUid(), entity.getEnvCreateUname()
                    , entity.getEnvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getEnvId());

        }
        return BaseResponse.ok(entity.getEnvId());

    }

    @Override
    public BaseResponse getConEnVerifyList(ConEnVerifyRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_En_Verify", uId, "Env_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Env_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEnVerifyRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("envContractorId").put("envCode")
                .put("envProjectName").put("pId").put("envState");
        IPage<ConEnVerifyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEnVerifyResponse> datas = baseMapper.getConEnVerifyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEnVerifyById(String envId) {
        ConEnVerifyResponse data = baseMapper.getConEnVerifyById(envId);
        return BaseResponse.ok(data);
    }
}
