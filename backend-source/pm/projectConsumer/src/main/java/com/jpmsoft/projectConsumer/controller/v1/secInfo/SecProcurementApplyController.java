package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecProcurementApplyDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:40
 */
@Slf4j
@RestController
@Api(tags = "招标采购申请", value = "招标采购申请")
@RequestMapping("/v1/procurementApply")
public class SecProcurementApplyController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标采购申请")
    @PostMapping("/createOrUpdateSecProcurementApply")
    public BaseResponse createOrUpdateSecProcurementApply(@RequestBody SecProcurementApplyDto request) {
        log.info("createOrUpdateSecProcurementApply");
        return secFeign.createOrUpdateSecProcurementApply(request);
    }

    @ApiOperation(value = "ID查询招标采购申请")
    @GetMapping("/getSecProcurementApplyById")
    public BaseResponse getSecProcurementApplyById(@RequestParam String id) {
        log.info("getSecProcurementApplyById");
        return secFeign.getSecProcurementApplyById(id);
    }

}
