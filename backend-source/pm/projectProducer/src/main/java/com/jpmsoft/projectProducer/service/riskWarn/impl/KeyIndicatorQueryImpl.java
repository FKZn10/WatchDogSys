package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.common.model.response.RiskWarn.RiskWarnIndexResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskWarnMapper;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.service.riskWarn.IKeyIndicatorQueryService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyIndicatorQueryImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements IKeyIndicatorQueryService {
    @Override
    public BaseResponse getKeyIndicatorQuery(RiskWarnIndexRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("starttime", request.getStartdata());
        params.put("Endtime", request.getEnddata());
        params.put("Rwi_Type", ValidateUtil.isEmpty(request.getRwiType()) ? null : request.getRwiType());
        params.put("rwiName", ValidateUtil.isEmpty(request.getRwiName()) ? null : request.getRwiName());
        params.put("dId", ValidateUtil.isEmpty(request.getdId()) ? null : request.getdId());
        params.put("rwiLevel", ValidateUtil.isEmpty(request.getRwiLevel()) ? null : request.getRwiLevel());
        params.put("rwiDesc", ValidateUtil.isEmpty(request.getRwiDesc()) ? null : request.getRwiDesc());
        params.put("rwiProblem", ValidateUtil.isEmpty(request.getRwiProblem()) ? null : request.getRwiProblem());
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? null : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? null : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        List<RiskWarnIndexResponse> List = baseMapper.getKeyIndicatorQuery(params);
        Integer total = baseMapper.getKeyIndicatorQueryCount(params);
        return BaseResponse.ok(List, total);
    }
}
