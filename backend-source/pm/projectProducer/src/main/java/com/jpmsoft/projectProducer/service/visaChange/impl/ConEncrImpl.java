package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConEncrRequest;
import com.jpmsoft.common.model.response.DesignChange.ConEncrResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConEncrMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEncr;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConEncrService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConEncrImpl extends ServiceImpl<ConEncrMapper, ConEncr> implements IConEncrService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "encrId", codeParam = "encrCode", method = "setEncrCode", codeRule = "ConEncr", beanClass = ConEncrRequest.class)
    public BaseResponse createOrUpdateConEncr(ConEncrRequest request) {
        ConEncr entity = new ConEncr();
        if (ValidateUtil.isEmpty(request.getEncrCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getEncrId())) {
            entity.setEncrId(GenerateUUID.getUUID());
            entity.setEncrCreateTime(new Date());
            entity.setEncrStateTime(null);
            entity.setEncrState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEncrId(), request.gettId(), entity.getEncrState() + "", entity.getEncrCreateUid(), entity.getEncrCreateUname()
                    , entity.getEncrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getEncrId());

        } else {
            ConEncr model = baseMapper.selectById(request.getEncrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEncrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEncrCreateTime(model.getEncrCreateTime());
            entity.setEncrCreateUid(model.getEncrCreateUid());
            entity.setEncrCreateUname(model.getEncrCreateUname());
            entity.setEncrState(model.getEncrState());
            entity.setEncrStateTime(model.getEncrStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEncrId(), request.gettId(), entity.getEncrState() + "", entity.getEncrCreateUid(), entity.getEncrCreateUname()
                    , entity.getEncrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getEncrId());

        }
        return BaseResponse.ok(entity.getEncrId());
    }

    @Override
    public BaseResponse getConEncrList(ConEncrRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Encr", uId, "Encr_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Encr_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEncrRequest> params = getFilter(request, authSql);
        IPage<ConEncrResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEncrResponse> datas = baseMapper.getConEncrList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEncrListNoAuth(ConEncrRequest request) {
        QueryMap<ConEncrRequest> params = getFilter(request, null);
        IPage<ConEncrResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEncrResponse> datas = baseMapper.getConEncrList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<ConEncrRequest> getFilter(ConEncrRequest request, String authSql) {
        QueryMap<ConEncrRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("encrCode").put("encrProjectName")
                .put("encrMajor").put("encrTitle").put("encrProposeId").put("encrState").put("pId");
        return params;
    }

    @Override
    public BaseResponse getConEncrById(String encrId) {
        ConEncrResponse data = baseMapper.getConEncrById(encrId);
        return BaseResponse.ok(data);
    }
}
