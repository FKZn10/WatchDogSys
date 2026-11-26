package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.peinfo.PeMoneyReturnDTO;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:43
 */
@Slf4j
@RestController
@Api(tags = "履约保证金退还", value = "履约保证金退还")
@RequestMapping("/v1/peMoneyReturn")
public class PeMoneyReturnController {

    @Resource
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "新增修改履约保证金退还")
    @PostMapping("/createOrUpdatePeMoneyReturn")
    public BaseResponse createOrUpdatePeMoneyReturn(@RequestBody PeMoneyReturnDTO request) {
        log.info("createOrUpdatePeMoneyReturn");
        return peInfoFeign.createOrUpdatePeMoneyReturn(request);
    }

    @ApiOperation(value = "分页查询履约保证金退还")
    @PostMapping("/getPeMoneyReturnList")
    public BaseResponse getPeMoneyReturnList(@RequestBody PeMoneyReturnDTO request, @RequestHeader String token) {
        log.info("getPeMoneyReturnList");
        return peInfoFeign.getPeMoneyReturnList(request, token);
    }

    @ApiOperation(value = "ID查询履约保证金退还")
    @GetMapping("/getPeMoneyReturnById")
    public BaseResponse getPeMoneyReturnById(@RequestParam String id) {
        log.info("getPeMoneyReturnById");
        return peInfoFeign.getPeMoneyReturnById(id);
    }

    @ApiOperation(value = "合同查询审批通过的款额总值")
    @GetMapping("/getPeMoneySumByConId")
    public BaseResponse getPeMoneySumByConId(@RequestParam String conId) {
        log.info("getPeMoneySumByConId");
        return peInfoFeign.getPeMoneySumByConId(conId);
    }

}
