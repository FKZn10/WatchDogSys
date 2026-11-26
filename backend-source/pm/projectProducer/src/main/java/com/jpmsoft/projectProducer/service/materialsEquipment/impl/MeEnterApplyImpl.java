package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyRequest;
import com.jpmsoft.common.model.response.MeEnter.MeEnterApplyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeEnterApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApply;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MeEnterApplyImpl extends ServiceImpl<MeEnterApplyMapper, MeEnterApply> implements IMeEnterApplyService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IMeEnterApplyDtlService meEnterApplyDtlService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "eaId", codeParam = "eaCode", method = "setEaCode", codeRule = "MeEnterApply", beanClass = MeEnterApplyRequest.class)
    public BaseResponse createOrUpdateMeEnterApply(MeEnterApplyRequest request) {
        MeEnterApply entity = new MeEnterApply();
        if (ValidateUtil.isEmpty(request.getEaCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<MeEnterApply> wrapper = new QueryWrapper<>();
        wrapper.eq("Ea_Code", request.getEaCode());
        if (!ValidateUtil.isEmpty(request.getEaId()))
            wrapper.ne("Ea_Id", request.getEaId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getEaId())) {
            entity.setEaId(GenerateUUID.getUUID());
            entity.setEaCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEaId(), request.gettId(), entity.getEaState() + "", entity.getEaCreateUid(), entity.getEaCreateUname()
                    , entity.getEaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), entity.getEaFileAp(), entity.getEaFileCd(), entity.getEaFileInvoice(), entity.getEaFileMas(), entity.getEaFileNmc(), entity.getEaFilePc(), entity.getEaFilePtr()), entity.getEaId());

        } else {
            MeEnterApply model = baseMapper.selectById(request.getEaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEaState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEaCreateTime(model.getEaCreateTime());
            entity.setEaCreateUid(model.getEaCreateUid());
            entity.setEaCreateUname(model.getEaCreateUname());
            entity.setEaState(model.getEaState());
            entity.setEaStateTime(model.getEaStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEaId(), request.gettId(), entity.getEaState() + "", entity.getEaCreateUid(), entity.getEaCreateUname()
                    , entity.getEaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(
                    Arrays.asList(request.getFileRelationId(), entity.getEaFileAp(), entity.getEaFileCd(), entity.getEaFileInvoice(), entity.getEaFileMas(), entity.getEaFileNmc(), entity.getEaFilePc(), entity.getEaFilePtr()),
                    Arrays.asList(request.getFileIds(), request.getEaFileApIds(), request.getEaFileCdIds(), request.getEaFileInvoiceIds(), request.getEaFileMasIds(), request.getEaFileNmcIds(), request.getEaFilePcIds(), request.getEaFilePtrIds()), entity.getEaId());

        }
        meEnterApplyDtlService.createOrUpdateMeEnterApplyDtls(request.getApplyDtls(), entity.getEaId());
        return BaseResponse.ok(entity.getEaId());
    }

    @Override
    public BaseResponse getMeEnterApplyList(MeEnterApplyRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Enter_Apply", uId, "Ea_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ea_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeEnterApplyRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("conId").put("eaPurchaseContract").put("pName")
                .put("conName").put("eaPurchaseContractName").put("eaCode").put("eaSupervisorId")
                .put("eaDesignId").put("eaWitness").put("eaProjectTeam").put("conPartyBId");
        IPage<MeEnterApplyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeEnterApplyResponse> datas = baseMapper.getMeEnterApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeEnterApplyByEaId(String eaId) {
        MeEnterApplyResponse data = baseMapper.getMeEnterApplyByEaId(eaId);
        return BaseResponse.ok(data);
    }

    @Override
    public MeEnterApply getMeEnterApplyPojoByEaId(String eaId) {
        QueryWrapper<MeEnterApply> wrapper = new QueryWrapper<>();
        wrapper.eq("Ea_Id", eaId);
        return baseMapper.selectOne(wrapper);
    }
}
