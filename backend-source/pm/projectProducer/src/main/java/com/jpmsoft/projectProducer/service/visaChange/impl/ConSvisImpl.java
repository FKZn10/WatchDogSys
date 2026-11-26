package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConSvisRequest;
import com.jpmsoft.common.model.response.DesignChange.ConSvisResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConSvisMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvis;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConSvisService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConSvisImpl extends ServiceImpl<ConSvisMapper, ConSvis> implements IConSvisService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "svisId", codeParam = "svisCode", method = "setSvisCode", codeRule = "ConSvis", beanClass = ConSvisRequest.class)
    public BaseResponse createOrUpdateConSvis(ConSvisRequest request) {
        ConSvis entity = new ConSvis();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSvisCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<ConSvis> wrapper = new QueryWrapper<>();
        wrapper.eq("Svis_Code", request.getSvisCode());
        if (!ValidateUtil.isEmpty(entity.getSvisId()))
            wrapper.ne("Svis_Id", request.getSvisId());
        wrapper.ne("Svis_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getSvisId())) {
            entity.setSvisId(GenerateUUID.getUUID());
            entity.setSvisCreateTime(new Date());
            entity.setSvisStateTime(null);
            entity.setSvisState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSvisId(), request.gettId(), entity.getSvisState() + "", entity.getSvisCreateUid(), entity.getSvisCreateUname()
                    , entity.getSvisCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getSvisId());

        } else {
            ConSvis model = baseMapper.selectById(request.getSvisId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSvisState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSvisCreateTime(model.getSvisCreateTime());
            entity.setSvisCreateUid(model.getSvisCreateUid());
            entity.setSvisCreateUname(model.getSvisCreateUname());
            entity.setSvisState(model.getSvisState());
            entity.setSvisStateTime(model.getSvisStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSvisId(), request.gettId(), entity.getSvisState() + "", entity.getSvisCreateUid(), entity.getSvisCreateUname()
                    , entity.getSvisCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getSvisId());

        }
        return BaseResponse.ok(entity.getSvisId());
    }

    @Override
    public BaseResponse getConSvisList(ConSvisRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Svis", uId, "Svis_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Svis_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSvisRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("svisName").put("svisCode").put("svisProjectName").put("svisMainCode")
                .put("svisMajor").put("svisDrawingNo").put("pName").put("conName").put("filterSvBudget")
                .put("svisProposedId").put("pId").put("conId").put("svisState").put("svisSubCode");
        List<ConSvisResponse> datas = baseMapper.getConSvisList(params.getQuery());
        Integer total = baseMapper.getConSvisListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getConSvisById(String svisId) {
        ConSvisResponse data = baseMapper.getConSvisById(svisId);
        return BaseResponse.ok(data);
    }
}
