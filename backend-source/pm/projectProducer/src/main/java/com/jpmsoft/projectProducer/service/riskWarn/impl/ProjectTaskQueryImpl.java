package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskWarnMapper;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.service.riskWarn.IProjectTaskQueryService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProjectTaskQueryImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements IProjectTaskQueryService {
    @Override
    public BaseResponse getProjectTaskQueryCount(RiskWarQueryRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("starttime", JudgeUtil.checkObject(request.getStartTime()) ? null : request.getStartTime());
        params.put("Endtime", JudgeUtil.checkObject(request.getEndTime()) ? null : request.getEndTime());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("uaranteeprocessed", JudgeUtil.checkObject(request.getUaranteeprocessed()) ? null : request.getUaranteeprocessed());
        params.put("pName", JudgeUtil.checkObject(request.getpName()) ? null : request.getpName());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        List<Map<String, Object>> datas = baseMapper.getProjectTaskQuery(params);
        List<Map<String, Object>> total = baseMapper.getProjectTaskQueryCount(params);
        return BaseResponse.ok(datas, total.size());

    }
}
