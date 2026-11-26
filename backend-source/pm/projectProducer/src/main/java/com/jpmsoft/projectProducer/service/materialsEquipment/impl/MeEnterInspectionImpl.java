package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.MeEnter.MeEnterInspectionRequest;
import com.jpmsoft.common.model.response.MeEnter.MeEnterInspectionResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeEnterInspectionMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterInspection;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterInspectionService;
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
public class MeEnterInspectionImpl extends ServiceImpl<MeEnterInspectionMapper, MeEnterInspection> implements IMeEnterInspectionService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IMeEnterApplyDtlService meEnterApplyDtlService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "eiId", codeParam = "eiCode", method = "setEiCode", codeRule = "MeEnterInspection", beanClass = MeEnterInspectionRequest.class)
    public BaseResponse createOrUpdateMeEnterInspection(MeEnterInspectionRequest request) {
        MeEnterInspection entity = new MeEnterInspection();
        if (ValidateUtil.isEmpty(request.getEiCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getEiId())) {
            entity.setEiCreateTime(new Date());
            entity.setEiId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEiId(), request.gettId(), entity.getEiState() + "", entity.getEiCreateUid(), entity.getEiCreateUname()
                    , entity.getEiCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getEiAcceptPic()), entity.getEiId());

        } else {
            MeEnterInspection model = baseMapper.selectById(request.getEiId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEiState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEiState(model.getEiState());
            entity.setEiStateTime(model.getEiStateTime());
            entity.setEiCreateTime(model.getEiCreateTime());
            entity.setEiCreateUid(model.getEiCreateUid());
            entity.setEiCreateUname(model.getEiCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEiId(), request.gettId(), entity.getEiState() + "", entity.getEiCreateUid(), entity.getEiCreateUname()
                    , entity.getEiCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getEiAcceptPic()),
                    Arrays.asList(request.getFileIds(), request.getEiAcceptPicIds()), entity.getEiId());

        }
        meEnterApplyDtlService.updateMeEnterApplyDtlsByBath(request.getApplyDtls());
        return BaseResponse.ok(entity.getEiId());
    }

    @Override
    public BaseResponse getMeEnterInspectionList(MeEnterInspectionRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Enter_Inspection", uId, "Ei_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ei_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeEnterInspectionRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conId")
                .put("conName").put("conPartyBId").put("eiAcceptTime").put("eaCode");
        IPage<MeEnterInspectionResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeEnterInspectionResponse> datas = baseMapper.getMeEnterInspectionList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeEnterInspectionByEiId(String eiId) {
        MeEnterInspectionResponse data = baseMapper.getMeEnterInspectionByEiId(eiId);
        return BaseResponse.ok(data);
    }
}
