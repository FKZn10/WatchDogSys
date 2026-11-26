package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskWarnMapper;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.service.riskWarn.IDepartmentTaskQueryService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentTaskQueryImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements IDepartmentTaskQueryService {
    @Override
    public BaseResponse getDepartmentTaskQueryCount(RiskWarQueryRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("starttime", JudgeUtil.checkObject(request.getStartTime()) ? null : request.getStartTime());
        params.put("Endtime", JudgeUtil.checkObject(request.getEndTime()) ? null : request.getEndTime());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("uaranteeprocessed", JudgeUtil.checkObject(request.getUaranteeprocessed()) ? null : request.getUaranteeprocessed());
        params.put("cName", JudgeUtil.checkObject(request.getcName()) ? null : request.getcName());
        params.put("teId", JudgeUtil.checkObject(request.getTeId()) ? null : request.getTeId());
        List<Map<String, Object>> datas = baseMapper.getDepartmentTaskQuery(params);
        List<Map<String, Object>> total = baseMapper.getDepartmentTaskQueryCount(params);
        return BaseResponse.ok(datas, total.size());
    }
}
