package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessDtlRequest;
import com.jpmsoft.common.model.response.MeEnter.MeWitnessDtlResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeWitnessDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeWitnessDtl;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeWitnessDtlService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeWitnessDtlImpl extends ServiceImpl<MeWitnessDtlMapper, MeWitnessDtl> implements IMeWitnessDtlService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public void crateOrUpdateMeWitnessDtl(List<MeWitnessDtlRequest> datas, String wiId) {
        deleteAllMeWitnessDtlByWiId(wiId);
        if (datas != null && datas.size() > 0) {
            List<MeWitnessDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                MeWitnessDtl entity = new MeWitnessDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setWdCreateTime(data.getWdCreateTime() == null ? new Date() : data.getWdCreateTime());
                entity.setWdId(GenerateUUID.getUUID());
                entity.setWiId(wiId);
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void updateValueNull(String wiId) {
        if (ValidateUtil.isNotEmpty(wiId)) baseMapper.updateValueNull(wiId);
    }

    @Override
    public BaseResponse getMeWitnessDtlList(MeWitnessDtlRequest request, String uId) {
        QueryMap<MeWitnessDtlRequest> params = getParams(request, uId);
        IPage<MeWitnessDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeWitnessDtlResponse> datas = baseMapper.getMeWitnessDtlList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeWitnessDtlInspeList(MeWitnessDtlRequest request, String uId) {
        QueryMap<MeWitnessDtlRequest> params = getParams(request, uId);
        IPage<MeWitnessDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeWitnessDtlResponse> datas = baseMapper.getMeWitnessDtlInspeList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }


    private QueryMap<MeWitnessDtlRequest> getParams(MeWitnessDtlRequest request, String uId) {
        //根据见证送检鉴权
        String authSql = sysUserService.getAuthSql("Me_Witness_Inspection", uId, "Wi_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<MeWitnessDtlRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("wiUnitId").put("pId").put("conId").put("wiId").put("wdName")
                .put("metName").put("eaCode").put("haveReport").put("wiState").put("wdCode").put("wdResult");
        return params;
    }

    private void deleteAllMeWitnessDtlByWiId(String wiId) {
        QueryWrapper<MeWitnessDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Wi_Id", wiId);
        baseMapper.delete(wrapper);
    }
}
