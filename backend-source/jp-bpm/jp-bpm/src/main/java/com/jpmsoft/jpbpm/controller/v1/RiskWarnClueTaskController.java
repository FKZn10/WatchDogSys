package com.jpmsoft.jpbpm.controller.v1;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.service.RiskWarnClueTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/v1/riskWarnClueTask")
public class RiskWarnClueTaskController {
    @Autowired private RiskWarnClueTaskService service;
    @PostMapping("/sync")
    public BaseResponse sync(@RequestParam("rwcId") String rwcId, @RequestParam("operator") String operator,
                             @RequestHeader(value = "token", required = false) String token) {
        return service.sync(rwcId, operator, token);
    }
}
