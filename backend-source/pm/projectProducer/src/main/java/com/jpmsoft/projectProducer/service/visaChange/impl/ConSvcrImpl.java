package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConSvcrRequest;
import com.jpmsoft.common.model.response.DesignChange.ConSvcrResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConSvcrMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvcr;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConSvcrService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConSvcrImpl extends ServiceImpl<ConSvcrMapper, ConSvcr> implements IConSvcrService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "svcrId", codeParam = "svcrCode", method = "setSvcrCode", codeRule = "ConSvcr", beanClass = ConSvcrRequest.class)
    public BaseResponse createOrUpdateConSvcr(ConSvcrRequest request) {
        ConSvcr entity = new ConSvcr();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSvcrCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);

        QueryWrapper<ConSvcr> wrapper = new QueryWrapper<>();
        wrapper.eq("Svv_Id", request.getSvvId());
        if (!ValidateUtil.isEmpty(entity.getSvcrId()))
            wrapper.ne("Svcr_Id", request.getSvcrId());
        wrapper.ne("Svcr_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.recordExist);

        if (ValidateUtil.isEmpty(request.getSvcrId())) {
            entity.setSvcrId(GenerateUUID.getUUID());
            entity.setSvcrCreateTime(new Date());
            entity.setSvcrStateTime(null);
            entity.setSvcrState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSvcrId(), request.gettId(), entity.getSvcrState() + "", entity.getSvcrCreateUid(), entity.getSvcrCreateUname()
                    , entity.getSvcrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getSvcrId());

        } else {
            ConSvcr model = baseMapper.selectById(request.getSvcrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSvcrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSvcrCreateTime(model.getSvcrCreateTime());
            entity.setSvcrCreateUid(model.getSvcrCreateUid());
            entity.setSvcrCreateUname(model.getSvcrCreateUname());
            entity.setSvcrState(model.getSvcrState());
            entity.setSvcrStateTime(model.getSvcrStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSvcrId(), request.gettId(), entity.getSvcrState() + "", entity.getSvcrCreateUid(), entity.getSvcrCreateUname()
                    , entity.getSvcrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getSvcrId());

        }
        return BaseResponse.ok(entity.getSvcrId());
    }

    @Override
    public BaseResponse getConSvcrList(ConSvcrRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Svcr", uId, "Svcr_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Svcr_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSvcrRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("svcrCode").put("svcrProjectName").put("svcrMajor").put("pName")
                .put("conName").put("svcrProposeId").put("pId").put("svcrState");
        IPage<ConSvcrResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConSvcrResponse> datas = baseMapper.getConSvcrList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConSvcrById(String svcrId) {
        ConSvcrResponse data = baseMapper.getConSvcrById(svcrId);
        return BaseResponse.ok(data);
    }
}
