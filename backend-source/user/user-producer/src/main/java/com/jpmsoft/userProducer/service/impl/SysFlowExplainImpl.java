package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysFlowExplainMapper;
import com.jpmsoft.userProducer.pojo.entity.SysFlowExplain;
import com.jpmsoft.userProducer.service.ISysFlowExplainService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysFlowExplainImpl extends ServiceImpl<SysFlowExplainMapper, SysFlowExplain> implements ISysFlowExplainService {
    @Override
    public BaseResponse createOrUpdateFlowExplain(SysFlowExplain request) {
        if (ValidateUtil.isNotEmpty(request.getFeId())) {
            SysFlowExplain entity = baseMapper.selectById(request.getFeId());
            if (entity == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
            request.setFeTime(entity.getFeTime());
            baseMapper.updateById(request);
        } else {
            request.setFeTime(new Date());
            request.setFeId(GenerateUUID.getUUID());
            baseMapper.insert(request);
        }
        return BaseResponse.ok(request.getFeId());
    }

    @Override
    public BaseResponse getFlowExplainList(SysFlowExplain request) {
        QueryWrapper<SysFlowExplain> queryWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getFeMemberId()))
            queryWrapper.eq("FE_MemberID", request.getFeMemberId());
        if (!ValidateUtil.isEmpty(request.getFeSolutionId()))
            queryWrapper.eq("FE_SolutionID", request.getFeSolutionId());
        if (!ValidateUtil.isEmpty(request.getFeContent()))
            queryWrapper.like("FE_Content", request.getFeContent());
        List<SysFlowExplain> datas = baseMapper.selectList(queryWrapper);
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse delFlowExplainById(String feId) {
        SysFlowExplain entity = baseMapper.selectById(feId);
        if (entity == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(feId);
        return BaseResponse.ok();
    }
}
