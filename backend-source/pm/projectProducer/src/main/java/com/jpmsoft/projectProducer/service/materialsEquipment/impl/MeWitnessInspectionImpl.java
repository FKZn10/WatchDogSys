package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessInspectionRequest;
import com.jpmsoft.common.model.response.MeEnter.MeWitnessInspectionResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeWitnessInspectionMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeWitnessInspection;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeWitnessDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeWitnessInspectionService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MeWitnessInspectionImpl extends ServiceImpl<MeWitnessInspectionMapper, MeWitnessInspection> implements IMeWitnessInspectionService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IMeWitnessDtlService meWitnessDtlService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "wiId", codeParam = "wiCode", method = "setWiCode", codeRule = "MeWitnessInspection", beanClass = MeWitnessInspectionRequest.class)
    public BaseResponse createOrUpdateMeWitnessInspection(MeWitnessInspectionRequest request) {
        MeWitnessInspection entity = new MeWitnessInspection();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<MeWitnessInspection> wrapper = new QueryWrapper<>();
        wrapper.eq("Wi_Order_No", request.getWiOrderNo());
        if (!ValidateUtil.isEmpty(request.getWiId())) wrapper.ne("Wi_Id", request.getWiId());
        if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(ResMsg.wiOrderNoExists);
        if (ValidateUtil.isEmpty(request.getWiId())) {
            entity.setWiId(GenerateUUID.getUUID());
            entity.setWiCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getWiId(), request.gettId(), entity.getWiState() + "", entity.getWiCreateUid(), entity.getWiCreateUname(), entity.getWiCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getWiPicture(), request.getWiSamplingSupervisor(), request.getFileRelationId()), entity.getWiId());
        } else {
            MeWitnessInspection model = baseMapper.selectById(request.getWiId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getWiId(), request.gettId(), entity.getWiState() + "", entity.getWiCreateUid(), entity.getWiCreateUname(), entity.getWiCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getWiPicture(), request.getWiSamplingSupervisor(), request.getFileRelationId()), Arrays.asList(request.getWiPictureIds(), request.getWiSamplingSupervisorIds(), request.getFileIds()), entity.getWiId());
        }
        meWitnessDtlService.crateOrUpdateMeWitnessDtl(request.getWitnessDtls(), entity.getWiId());
        return BaseResponse.ok(entity.getWiId());
    }

    @Override
    public BaseResponse updateMeWitnessInspectionDtl(MeWitnessInspectionRequest request) {
        MeWitnessInspection entity = baseMapper.selectById(request.getWiId());
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        meWitnessDtlService.crateOrUpdateMeWitnessDtl(request.getWitnessDtls(), request.getWiId());
        return BaseResponse.ok(request.getWiId());
    }

    @Override
    public BaseResponse return2InitWitnessInspectionDtl(String wiId) {
        MeWitnessInspection entity = baseMapper.selectById(wiId);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getWiState(), ApprovalStatusEnum.Returned_Initiator.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        meWitnessDtlService.updateValueNull(wiId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getMeWitnessInspectionList(MeWitnessInspectionRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Witness_Inspection", uId, "Wi_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Wi_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeWitnessInspectionRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conName").put("pId").put("pName").put("conId")
                .put("wiUnitId").put("wiOrderNo").put("eadId").put("eaId").put("conPartyBId")
                .put("wiCode").put("wiDetectionCode");
        IPage<MeWitnessInspectionResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeWitnessInspectionResponse> datas = baseMapper.getMeWitnessInspectionList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeWitnessInspectionByWiId(String wiId) {
        MeWitnessInspectionResponse data = baseMapper.getMeWitnessInspectionByWiId(wiId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getMeWitnessInspectionByConId(String conId) {
        return BaseResponse.ok(baseMapper.getMeWitnessInspectionByConId(conId));
    }

    @Override
    public BaseResponse getMeWitnessInspectionByOrderNo(String wiOrderNo) {
        return BaseResponse.ok(baseMapper.getMeWitnessInspectionByOrderNo(wiOrderNo));
    }
}
