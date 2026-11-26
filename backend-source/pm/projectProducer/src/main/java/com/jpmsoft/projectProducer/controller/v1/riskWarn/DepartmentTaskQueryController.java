package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.riskWarn.IDepartmentTaskQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("riskWarn/v1/departmentTaskQuery")
public class DepartmentTaskQueryController {
    @Resource
    private IDepartmentTaskQueryService departmentTaskQueryService;

    /**
     * 获取项目预警信息
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getDepartmentTaskQueryCount")
    public BaseResponse getDepartmentTaskQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token) {
        log.info("getDepartmentTaskQueryCount");
        return departmentTaskQueryService.getDepartmentTaskQueryCount(request);
    }
}
