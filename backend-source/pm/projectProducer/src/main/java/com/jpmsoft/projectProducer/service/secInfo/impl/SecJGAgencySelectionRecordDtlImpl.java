package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGAgencySelectionRecordDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGAgencySelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGAgencySelectionRecordDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecordDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGAgencySelectionRecordDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SecJGAgencySelectionRecordDtlImpl extends ServiceImpl<SecJGAgencySelectionRecordDtlMapper, SecJGAgencySelectionRecordDtl> implements ISecJGAgencySelectionRecordDtlService {


    @Override
    public void createOrUpdateSecSelectionRecord(List<SecJGAgencySelectionRecordDtlModel> datas, String srId) {
        deleteAllBySrId(srId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecJGAgencySelectionRecordDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecJGAgencySelectionRecordDtl entity = new SecJGAgencySelectionRecordDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setId(ValidateUtil.isEmpty(entity.getId()) ? GenerateUUID.getUUID() : entity.getId());
                entity.setSrId(srId);
                entity.setSrdTime(ValidateUtil.isNull(data.getSrdTime()) ? new Date() : data.getSrdTime());
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse getJGAgencySelectionRecordDtlCount(String exId) {
        return null;
    }

    @Override
    public BaseResponse updateSecJGAgencySelectionRecordDtl(SecJGAgencySelectionRecordDtlModel request) {
        if (ValidateUtil.isEmpty(request.getId())) return BaseResponse.fail(ResMsg.notId);
        SecJGAgencySelectionRecordDtl data=baseMapper.selectById(request.getId());
        data.setSrdAttend(request.getSrdAttend());
        data.setSrdAvoid(request.getSrdAvoid());
        data.setSrdConfirmTime(request.getSrdConfirmTime());
        baseMapper.updateById(data);
        return BaseResponse.ok();
    }

    @Override
    public void deleteAllBySrId(String srId) {
        QueryWrapper<SecJGAgencySelectionRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Id", srId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecJGAgencySelectionRecordDtlModel> getSelectionRecordDtlList(SecJGAgencySelectionRecordDtlDto dto) {
        QueryMap<SecJGAgencySelectionRecordDtlDto> params = new QueryMap<>(dto);
        params.put("exId").put("srId").put("secId").put("sameBidTime")
                .put("notSecIdList").put("srdAttend");
        List<SecJGAgencySelectionRecordDtlModel> selectionRecordDtlList = baseMapper.getSelectionRecordDtlList(params.getQuery());
        return selectionRecordDtlList;
    }
}
