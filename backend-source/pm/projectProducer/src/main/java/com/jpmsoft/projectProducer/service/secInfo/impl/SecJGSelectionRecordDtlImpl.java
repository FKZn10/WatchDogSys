package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGSelectionRecordDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecordDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGSelectionRecordDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecJGSelectionRecordDtlImpl extends ServiceImpl<SecJGSelectionRecordDtlMapper, SecJGSelectionRecordDtl> implements ISecJGSelectionRecordDtlService {



    @Override
    public void createOrUpdateSecSelectionRecord(List<SecJGSelectionRecordDtlModel> datas, String srId) {
        deleteAllBySrId(srId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecJGSelectionRecordDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecJGSelectionRecordDtl entity = new SecJGSelectionRecordDtl();
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
    public void deleteAllBySrId(String srId) {
        QueryWrapper<SecJGSelectionRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Id", srId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getJGSelectionRecordDtlCount(String exId) {
        Integer count = baseMapper.getJGSelectionRecordDtlCount(exId);
        return BaseResponse.ok(count == null ? 0 : count + 1);
    }

    @Override
    public BaseResponse updateSecJGSelectionRecordDtl(SecJGSelectionRecordDtlModel request) {
        if (ValidateUtil.isEmpty(request.getId())) return BaseResponse.fail(ResMsg.notId);
        SecJGSelectionRecordDtl data=baseMapper.selectById(request.getId());
        data.setSrdAttend(request.getSrdAttend());
        data.setSrdAvoid(request.getSrdAvoid());
        data.setSrdConfirmTime(request.getSrdConfirmTime());
        baseMapper.updateById(data);
        return BaseResponse.ok();
    }

    @Override
    public List<SecJGSelectionRecordDtlModel> getSelectionRecordDtlList(SecJGSelectionRecordDtlDto dto) {
        QueryMap<SecJGSelectionRecordDtlDto> params = new QueryMap<>(dto);
        params.put("exId").put("srId").put("secId").put("sameBidTime")
                .put("notSecIdList").put("srdAttend");
        List<SecJGSelectionRecordDtlModel> selectionRecordDtlList = baseMapper.getSelectionRecordDtlList(params.getQuery());
        return selectionRecordDtlList;
    }


}
