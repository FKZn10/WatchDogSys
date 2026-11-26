package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConDcoRequest;
import com.jpmsoft.common.model.response.DesignChange.ConDcoResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConDcoMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDco;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEcm;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConDcoService;
import com.jpmsoft.projectProducer.service.visaChange.IConEcmService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ConDcoImpl
        extends ServiceImpl<ConDcoMapper, ConDco> implements IConDcoService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IConEcmService conEcmService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "dcoId", codeParam = "dcoCode", method = "setDcoCode", codeRule = "ConDco", beanClass = ConDcoRequest.class)
    public BaseResponse createOrUpdateConDco(ConDcoRequest request) {
        ConDco entity = new ConDco();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getDcoCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<ConDco> wrapper = new QueryWrapper<>();
        wrapper.eq("Dco_Code", request.getDcoCode());
        if (!ValidateUtil.isEmpty(entity.getDcoId()))
            wrapper.ne("Dco_Id", request.getDcoId());
        wrapper.ne("Dco_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getDcoId())) {
            entity.setDcoId(GenerateUUID.getUUID());
            entity.setDcoCreateTime(new Date());
            entity.setDcoStateTime(null);
            entity.setDcoState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getDcoId(), request.gettId(), entity.getDcoState() + "", entity.getDcoCreateUid(), entity.getDcoCreateUname()
                    , entity.getDcoCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getDcoDoc(), request.getDcoDrawing(), request.getDcoCostBook(),request.getDcoSupervisionFile(),request.getDcoConsultingFile(),request.getDcoReviewFile()), entity.getDcoId());

            return BaseResponse.ok(entity.getDcoId());
        } else {
            ConDco model = baseMapper.selectById(request.getDcoId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getDcoState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setDcoCreateTime(model.getDcoCreateTime());
            entity.setDcoCreateUid(model.getDcoCreateUid());
            entity.setDcoCreateUname(model.getDcoCreateUname());
            entity.setDcoState(model.getDcoState());
            entity.setDcoStateTime(model.getDcoStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getDcoId(), request.gettId(), entity.getDcoState() + "", entity.getDcoCreateUid(), entity.getDcoCreateUname()
                    , entity.getDcoCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getDcoDoc(), request.getDcoDrawing(), request.getDcoCostBook(),request.getDcoSupervisionFile(),request.getDcoConsultingFile(),request.getDcoReviewFile()),
                    Arrays.asList(request.getFileIds(), request.getDcoDocIds(), request.getDcoDrawingIds(), request.getDcoCostBookIds(),request.getDcoSupervisionFileIds(),request.getDcoConsultingFileIds(),request.getDcoReviewFileIds()), entity.getDcoId());
            return BaseResponse.ok(entity.getDcoId());
        }
    }

    @Override
    public BaseResponse getConDcoList(ConDcoRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Dco", uId, "Dco_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Dco_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConDcoRequest> params = setFilterParams(request, authSql);
        IPage<ConDcoResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConDcoResponse> datas = baseMapper.getConDcoList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConDcoListGm(ConDcoRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Dco", uId, "Dco_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dco_State");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<ConDcoRequest> params = setFilterParams(request, authSql);
        IPage<ConDcoResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConDcoResponse> datas = baseMapper.getConDcoList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConDcoListNoAuth(ConDcoRequest request) {
        QueryMap<ConDcoRequest> params = setFilterParams(request, null);
        IPage<ConDcoResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConDcoResponse> datas = baseMapper.getConDcoList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    public BaseResponse getConDcoByDcoId(String dcoId) {
        ConDcoResponse data = baseMapper.getConDcoByDcoId(dcoId);
        ConEcm ecm = conEcmService.getLastEcmByDcoId(dcoId);
        if (ecm != null) {
            data.setEcmId(ecm.getEcmId());
            data.setEcmName(ecm.getEcmName());
        }
        return BaseResponse.ok(data);
    }


    public QueryMap<ConDcoRequest> setFilterParams(ConDcoRequest request, String authSql) {
        QueryMap<ConDcoRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("dcoState").put("pId").put("pName").put("conId").put("conName").put("dcoName")
                .put("dcoCode").put("dcoProjectName").put("dcoMajor").put("dcoConstructionUnitId").put("dcoExceedBudget")
                .put("dcoDesignUnitId").put("dcoSupervisorId").put("dcoContractorId").put("dcoGrade").put("dcoIncrease")
                .put("dcoCreateUname").put("filterGrade").put("dcoIsHistory").put("dcoYesNo").put("dcoDep").put("dcoMainCode")
                .put("dcoContent").put("enisCode").put("enisLocation").put("dcoConstructionUnitName").put("filterDcBudget")
                .put("dcoContractorName").put("dcoSupervisorName").put("dcoDesignUnitName").put("dcoDepName").put("dcoSubCode")
                .put(request.getDcoCreateTimes(), "dcoCreateTimeStart", "dcoCreateTimeEnd")
                .put(request.getDcoEstimates(), "dcoEstimateStart", "dcoEstimateEnd")
                .put(request.getDcoDates(), "dcoDateStart", "dcoDateEnd")
                .put("filterConECFee");
        return params;
    }
}
