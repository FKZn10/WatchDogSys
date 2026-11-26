package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyDtlRequest;
import com.jpmsoft.common.model.response.MeEnter.MeEnterApplyDtlResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeEnterApplyDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApply;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApplyDtl;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeEnterApplyDtlImpl extends ServiceImpl<MeEnterApplyDtlMapper, MeEnterApplyDtl> implements IMeEnterApplyDtlService {

    @Resource
    private IMeEnterApplyService meEnterApplyService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateMeEnterApplyDtl(MeEnterApplyDtlRequest request) {
        MeEnterApply eap = meEnterApplyService.getMeEnterApplyPojoByEaId(request.getEaId());
        if (eap == null)
            return BaseResponse.fail(ResMsg.notReqParam);
        if (JudgeUtil.nonModifyState(eap.getEaState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        MeEnterApplyDtl entity = new MeEnterApplyDtl();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getEadId())) {
            entity.setEadId(GenerateUUID.getUUID());
            entity.setEadCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            MeEnterApplyDtl model = baseMapper.selectById(request.getEadId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setEadCreateTime(model.getEadCreateTime());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getEadId());
    }

    @Override
    public void createOrUpdateMeEnterApplyDtls(List<MeEnterApplyDtlRequest> applyDtls, String eaId) {
        deleteMeEnterApplyDtls(eaId);
        List<MeEnterApplyDtl> entitys = new ArrayList<>();
        applyDtls.forEach(data -> {
            MeEnterApplyDtl entity = new MeEnterApplyDtl();
            BeanUtils.copyProperties(data, entity);
            entity.setEaId(eaId);
            entity.setEadId(GenerateUUID.getUUID());
            entity.setEadCreateTime(ValidateUtil.isEmpty(data.getEadCreateTime()) ? new Date() : data.getEadCreateTime());
            entitys.add(entity);
        });
        if (entitys.size() > 0)
            saveBatch(entitys);
    }

    @Override
    public void updateMeEnterApplyDtlsByBath(List<MeEnterApplyDtlRequest> applyDtls) {
        if (applyDtls != null && applyDtls.size() > 0) {
            List<MeEnterApplyDtl> entitys = new ArrayList<>();
            applyDtls.forEach(data -> {
                MeEnterApplyDtl entity = new MeEnterApplyDtl();
                BeanUtils.copyProperties(data, entity);
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                updateBatchById(entitys);
        }
    }


    private void deleteMeEnterApplyDtls(String eaId) {
        QueryWrapper<MeEnterApplyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ea_Id", eaId);
        baseMapper.delete(wrapper);
    }


    @Override
    public BaseResponse getMeEnterApplyDtlList(MeEnterApplyDtlRequest request, String uId) {
        //关联主表 根据主表合同和创建人鉴权
        String authSql = sysUserService.getAuthSql("Me_Enter_Apply", uId, "Ea_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeEnterApplyDtlRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("conId").put("eaPurchaseContract").put("pName")
                .put("conName").put("eaPurchaseContractName").put("eaId").put("inspectionStatus").put("useApplyStatus")
                .put("enterInspectionStatus").put("pConstructionUnit").put("conPartyBId").put("eaState").put("eaCode")
                .put("eaDate").put("eadSupplierCode").put("eadSupplierType").put("uaId").put("eaWitness");
        IPage<MeEnterApplyDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeEnterApplyDtlResponse> datas = baseMapper.getMeEnterApplyDtlList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public boolean existWithMetId(String metId) {
        QueryWrapper<MeEnterApplyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Met_Id", metId);
        return baseMapper.exists(wrapper);
    }
}
