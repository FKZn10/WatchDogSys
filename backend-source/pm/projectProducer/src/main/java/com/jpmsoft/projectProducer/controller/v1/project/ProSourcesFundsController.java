package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/proSourcesFunds")
public class ProSourcesFundsController {

    @Resource
    private IProSourcesFundsService proSourcesFundsService;

    /**
     * 统计项目资金来源
     *
     * @param pIds
     * @return
     */
    @PostMapping("/getSourcesFundsSumByPids")
    public BaseResponse getSourcesFundsSumByPids(@RequestBody List<String> pIds) {
        log.info("getSourcesFundsSumByPids");
        return proSourcesFundsService.getSourcesFundsSumByPids(pIds);
    }


    @GetMapping("/getProSourcesFundsByPid")
    public BaseResponse getProSourcesFundsByPid(@RequestParam String pId) {
        log.info("getProSourcesFundsByPid");
        return BaseResponse.ok(proSourcesFundsService.getProSourcesFundsPojoByPid(pId));
    }
}
