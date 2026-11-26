package com.jpmsoft.projectProducer.service.supervision.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Supervisor.ConSupOnSiteRequest;
import com.jpmsoft.common.model.response.Supervisor.ConSupOnSiteResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.supervision.ConSupOnSiteMapper;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupOnSite;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.supervision.IConSupOnSiteService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConSupOnSiteImpl extends ServiceImpl<ConSupOnSiteMapper, ConSupOnSite> implements IConSupOnSiteService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "osId", codeParam = "osCode", method = "setOsCode", codeRule = "ConSupOnSite", beanClass = ConSupOnSiteRequest.class)
    public BaseResponse createOrUpdateConSupOnSite(ConSupOnSiteRequest request) {
        ConSupOnSite entity = new ConSupOnSite();
        if (ValidateUtil.isEmpty(request.getOsCode())) return BaseResponse.fail(ResMsg.codeIsEmpty);

        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getOsId())) {
            entity.setOsId(GenerateUUID.getUUID());
            entity.setOsCreateTime(new Date());
            entity.setOsState(ValidateUtil.isEmpty(request.getOsState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getOsState());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getOsId(), request.gettId(), entity.getOsState() + "", entity.getOsCreateUid(), entity.getOsCreateUname(), entity.getOsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getOsId());

        } else {
            ConSupOnSite model = baseMapper.selectById(request.getOsId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getOsState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setOsCreateTime(model.getOsCreateTime());
            entity.setOsCreateUid(model.getOsCreateUid());
            entity.setOsCreateUname(model.getOsCreateUname());
            entity.setOsState(model.getOsState());
            entity.setOsStateTime(model.getOsStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getOsId(), request.gettId(), entity.getOsState() + "", entity.getOsCreateUid(), entity.getOsCreateUname(), entity.getOsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getOsId());

        }
        return BaseResponse.ok(entity.getOsId());
    }

    @Override
    public BaseResponse getConSupOnSiteList(ConSupOnSiteRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Sup_On_Site", uId, "Os_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Os_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSupOnSiteRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("osContractorId").put("pName").put("conName").put("pId");
        IPage<ConSupOnSiteResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConSupOnSiteResponse> datas = baseMapper.getConSupOnSiteList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConSupOnSiteById(String osId) {
        ConSupOnSiteResponse data = baseMapper.getConSupOnSiteById(osId);
        return BaseResponse.ok(data);
    }
}
