package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "项目资金来源", value = "项目资金来源")
@RequestMapping("/v1/proSourcesFunds")
public class ProSourcesFundsController {
    @Autowired
    private PreFeign preFeign;

    /**
     * 统计项目资金来源
     *
     * @param pIds
     * @return
     */
    @PostMapping("/getSourcesFundsSumByPids")
    public BaseResponse getSourcesFundsSumByPids(@RequestBody List<String> pIds) {
        log.info("getSourcesFundsSumByPids");
        return preFeign.getSourcesFundsSumByPids(pIds);
    }

    @ApiOperation(value = "项目ID查询其下项目资金来源列表")
    @GetMapping("/getProSourcesFundsByPid")
    public BaseResponse getProSourcesFundsByPid(@RequestParam String pId) {
        log.info("getProSourcesFundsByPid");
        return preFeign.getProSourcesFundsByPid(pId);
    }
}
