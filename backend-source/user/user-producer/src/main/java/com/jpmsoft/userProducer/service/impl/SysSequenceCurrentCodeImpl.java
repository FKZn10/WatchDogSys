package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysSequenceCurrentCodeMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceCurrentDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceCurrentCode;
import com.jpmsoft.userProducer.service.ISysSequenceCurrentCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysSequenceCurrentCodeImpl extends ServiceImpl<SysSequenceCurrentCodeMapper, SysSequenceCurrentCode> implements ISysSequenceCurrentCodeService {
    @Override
    public SysSequenceCurrentCode getSysSequenceCurrentCode(String sId, String ccReset) {
        QueryWrapper<SysSequenceCurrentCode> wrapper = new QueryWrapper<>();
        wrapper.eq("S_Id", sId);
        wrapper.eq("Cc_Reset", ccReset);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<SysSequenceCurrentCode> getSysSequenceCurrentCodes(String sId, String rule) {
        return baseMapper.getSysSequenceCurrentCodes(sId, rule);
    }

    @Override
    public BaseResponse getSysSequenceCurrentCodeBySid(SysSequenceCurrentDto request) {
        QueryMap<SysSequenceCurrentDto> queryMap = new QueryMap<>(request);
        queryMap.put("sId");
        List<SysSequenceCurrentDto> datas = baseMapper.getSysSequenceCurrentCodeBySid(queryMap.getQuery());
        Integer total = baseMapper.getSysSequenceCurrentCodeBySidCount(queryMap.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse createOrUpdateSequenceCurrentCode(SysSequenceCurrentDto request) {
        if (ValidateUtil.isNotEmpty(request.getCcId())) {
            SysSequenceCurrentCode current = baseMapper.selectById(request.getCcId());
            if (current == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            current.setCcReset(request.getCcReset());
            current.setCcNo(request.getCcNo());
            baseMapper.updateById(current);
        } else {
            if (ValidateUtil.isEmpty(request.getsId())) return BaseResponse.fail(BaseMsg.sequenceIdIsNull);
            SysSequenceCurrentCode entity = new SysSequenceCurrentCode();
            BeanUtils.copyProperties(request, entity);
            entity.setCcId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse delSequenceCurrentCodeById(String id) {
        SysSequenceCurrentCode current = baseMapper.selectById(id);
        if (current == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

}
