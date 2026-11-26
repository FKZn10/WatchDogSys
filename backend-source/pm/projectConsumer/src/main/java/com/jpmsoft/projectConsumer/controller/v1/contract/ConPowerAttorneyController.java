package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPowerAttorneyDTO;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "授权委托书", value = "授权委托书")
@RequestMapping("/v1/conPowerAttorney")
public class ConPowerAttorneyController {
    @Resource
    private ConFeign conFeign;


    @ApiOperation(value = "新增或修改授权委托书")
    @PostMapping("/createOrUpdateConPowerAttorney")
    public BaseResponse createOrUpdateConPowerAttorney(@RequestBody ConPowerAttorneyDTO request) {
        log.info("createOrUpdateConPowerAttorney");
        return conFeign.createOrUpdateConPowerAttorney(request);
    }

    @ApiOperation(value = "分页查授权委托书")
    @PostMapping("/getConPowerAttorneyList")
    public BaseResponse getConPowerAttorneyList(@RequestBody ConPowerAttorneyDTO request, @RequestHeader String token) {
        log.info("getConPowerAttorneyList");
        return conFeign.getConPowerAttorneyList(request, token);
    }

    @ApiOperation(value = "ID查授权委托书")
    @GetMapping("/getConPowerAttorneyById")
    public BaseResponse getConPowerAttorneyById(@RequestParam String id) {
        log.info("getConPowerAttorneyById");
        return conFeign.getConPowerAttorneyById(id);
    }

    @ApiOperation(value = "合同审批通过创建授权委托书")
    @GetMapping("/contractApprove2Create")
    public BaseResponse contractApprove2Create(@RequestParam String conId) {
        log.info("contractApprove2Create");
        return conFeign.contractApprove2Create(conId);
    }
}
