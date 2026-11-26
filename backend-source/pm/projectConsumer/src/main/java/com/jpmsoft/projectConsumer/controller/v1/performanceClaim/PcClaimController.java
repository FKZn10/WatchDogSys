package com.jpmsoft.projectConsumer.controller.v1.performanceClaim;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Claim.PcClaimRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "履约索赔-索赔", value = "履约索赔-索赔")
@RequestMapping("/claim/v1/pcClaim")
public class PcClaimController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改索赔")
    @PostMapping("/createOrUpdatePcClaim")
    public BaseResponse createOrUpdatePcClaim(@RequestBody PcClaimRequest request) {
        log.info("createOrUpdatePcClaim");
        return baseFeign.createOrUpdatePcClaim(request);
    }

    @ApiOperation(value = "分页查询索赔")
    @PostMapping("/getPcClaimList")
    public BaseResponse getPcClaimList(@RequestBody PcClaimRequest request, @RequestHeader String token) {
        log.info("getPcClaimList");
        return baseFeign.getPcClaimList(request, token);
    }

    @ApiOperation(value = "ID查询索赔")
    @GetMapping("/getPcClaimById")
    public BaseResponse getPcClaimById(@RequestParam String claimId) {
        log.info("getPcClaimById");
        return baseFeign.getPcClaimById(claimId);
    }
}
