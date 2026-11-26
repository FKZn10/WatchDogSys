package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectConsumer.feign.ConFeign;
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
@Api(tags = "工程变更令", value = "工程变更令")
@RequestMapping("/v1/conEco")
public class ConEcoController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改工程变更令")
    @PostMapping("/createOrUpdateConEco")
    public BaseResponse createOrUpdateConEco(@RequestBody ConEcoDTO request) {
        log.info("createOrUpdateConEco");
        return conFeign.createOrUpdateConEco(request);
    }

    @ApiOperation(value = "分页查询工程变更令")
    @PostMapping("/getConEcoList")
    public BaseResponse getConEcoList(@RequestBody ConEcoDTO request, @RequestHeader String token) {
        log.info("getConEcoList");
        return conFeign.getConEcoList(request, token);
    }

    @ApiOperation(value = "ID查询工程变更令")
    @GetMapping("/getConEcoById")
    public BaseResponse getConEcoById(@RequestParam String ecoId) {
        log.info("getConEcoById");
        return conFeign.getConEcoById(ecoId);
    }
}
