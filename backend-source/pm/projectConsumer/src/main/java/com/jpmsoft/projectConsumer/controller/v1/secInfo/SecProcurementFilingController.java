package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProcurementFilingModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "招标采购备案", value = "招标采购备案")
@RequestMapping("/v1/procurementFiling")
public class SecProcurementFilingController {
    @Autowired
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改招标采购备案")
    @PostMapping("/createOrUpdateSecProcurementFiling")
    public BaseResponse createOrUpdateSecProcurementFiling(@RequestBody SecProcurementFilingModel request) {
        log.info("createOrUpdateSecProcurementFiling");
        return secFeign.createOrUpdateSecProcurementFiling(request);
    }

    @ApiOperation(value = "分页查询招标采购备案")
    @PostMapping("/getSecProcurementFilingList")
    public BaseResponse getSecProcurementFilingList(@RequestBody SecProcurementFilingModel request, @RequestHeader String token) {
        log.info("getSecProcurementFilingList");
        return secFeign.getSecProcurementFilingList(request, token);
    }


    @ApiOperation(value = "ID查询招标采购备案")
    @GetMapping("/getSecProcurementFilingById")
    public BaseResponse getSecProcurementFilingById(@RequestParam String pfId) {
        log.info("getSecProcurementFilingById");
        return secFeign.getSecProcurementFilingById(pfId);
    }


    @ApiOperation(value = "根据项目id 获取最新一条审批通过的记录")
    @GetMapping("/getSecProcurementFilingLastByPid")
    public BaseResponse getSecProcurementFilingLastByPid(@RequestParam String pId) {
        log.info("getSecProcurementFilingLastByPid");
        return secFeign.getSecProcurementFilingLastByPid(pId);
    }

    @ApiOperation(value = "审批通过生成审批通过的招标信息")
    @GetMapping("/approve2GenerateSecInfo")
    public BaseResponse approve2GenerateSecInfo(@RequestParam String pfId) {
        log.info("approve2GenerateSecInfo");
        return secFeign.approve2GenerateSecInfo(pfId);
    }

}
