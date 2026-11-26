package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysSequenceSetDtlMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDtlDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSetDtl;
import com.jpmsoft.userProducer.service.ISysSequenceSetDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysSequenceSetDtlImpl extends ServiceImpl<SysSequenceSetDtlMapper, SysSequenceSetDtl> implements ISysSequenceSetDtlService {

    @Override
    public BaseResponse createOrUpdateSysSequenceSetDtl(SysSequenceSetDtlDto request) {
        QueryWrapper<SysSequenceSetDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sd_Rule_Order", request.getSdRuleOrder());
        wrapper.eq("S_Id", request.getsId());
        if (!ValidateUtil.isEmpty(request.getSdId()))
            wrapper.ne("Sd_Id", request.getSdId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        SysSequenceSetDtl entity = new SysSequenceSetDtl();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSdId())) {
            entity.setSdId(GenerateUUID.getUUID());
            entity.setSdCreateTime(new Date());
            entity.setSdUpdateTime(null);
            entity.setSdUpdateUid(null);
            entity.setSdUpdateUname(null);
            baseMapper.insert(entity);
        } else {
            SysSequenceSetDtl model = baseMapper.selectById(request.getSdId());
            if (model == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setSdCreateTime(model.getSdCreateTime());
            entity.setSdCreateUid(model.getSdCreateUid());
            entity.setSdCreateUname(model.getSdCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getSdId());
    }

    @Override
    public BaseResponse getSysSequenceSetDtlList(SysSequenceSetDtlDto request) {
        QueryMap<SysSequenceSetDtlDto> queryMap = new QueryMap<>(request);
        queryMap.put("sId");
        List<SysSequenceSetDtlDto> datas = baseMapper.getSysSequenceSetDtlList(queryMap.getQuery());
        Integer total = baseMapper.getSysSequenceSetDtlListCount(queryMap.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse deleteSysSequenceSetDtlById(String sdId) {
        SysSequenceSetDtl entity = baseMapper.selectById(sdId);
        if (entity == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(sdId);
        return BaseResponse.ok();
    }
}
