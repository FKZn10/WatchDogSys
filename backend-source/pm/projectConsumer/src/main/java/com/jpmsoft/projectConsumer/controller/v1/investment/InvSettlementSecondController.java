package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.investment.InvSettlementSecondDto;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 11:30
 */
@Slf4j
@RestController
@Api(tags = "结算二审", value = "结算二审")
@RequestMapping("/v1/invSettlementSecond")
public class InvSettlementSecondController {

    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增修改结算二审")
    @PostMapping("/createOrUpdateInvSettlementSecond")
    public BaseResponse createOrUpdateInvSettlementSecond(@RequestBody InvSettlementSecondDto request) {
        log.info("createOrUpdateInvSettlementSecond");
        return invFeign.createOrUpdateInvSettlementSecond(request);
    }

    @ApiOperation(value = "分页查询结算二审")
    @PostMapping("/getInvSettlementSecondList")
    public BaseResponse getInvSettlementSecondList(@RequestBody InvSettlementSecondDto request, @RequestHeader String token) {
        log.info("getInvSettlementSecondList");
        return invFeign.getInvSettlementSecondList(request, token);
    }

    @ApiOperation(value = "ID查询结算二审")
    @GetMapping("/getInvSettlementSecondById")
    public BaseResponse getInvSettlementSecondById(@RequestParam String id) {
        log.info("getInvSettlementSecondById");
        return invFeign.getInvSettlementSecondById(id);
    }
}
