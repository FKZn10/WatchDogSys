package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecFormDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:18
 */
@Slf4j
@RestController
@Api(tags = "专家库-考核评价表", value = "专家库-考核评价表")
@RequestMapping("/v1/expert/secForm")
public class SecFormController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家考核评价表")
    @PostMapping("/createOrUpdateSecForm")
    public BaseResponse createOrUpdateSecForm(@RequestBody SecFormDTO request) {
        log.info("createOrUpdateSecForm");
        return secFeign.createOrUpdateSecForm(request);
    }

    @ApiOperation(value = "创建或修改专家考核评价表")
    @PostMapping("/createOrUpdateSecFormAndDetail")
    public BaseResponse createOrUpdateSecFormAndDetail(@RequestBody SecFormDTO request) {
        log.info("createOrUpdateSecFormAndDetail");
        return secFeign.createOrUpdateSecFormAndDetail(request);
    }

    @ApiOperation(value = "分页查询专家考核评价表")
    @PostMapping("/getSecFormList")
    public BaseResponse getSecFormList(@RequestBody SecFormDTO request) {
        log.info("getSecFormList");
        return secFeign.getSecFormList(request);
    }

    @ApiOperation(value = "ID查询专家考核评价表")
    @GetMapping("/getSecFormById")
    public BaseResponse getSecFormById(@RequestParam String id) {
        log.info("getSecFormById");
        return secFeign.getSecFormById(id);
    }

    @ApiOperation(value = "查询最新一条启用的数据")
    @GetMapping("/getSecFormLatest")
    public BaseResponse getSecFormLatest(@RequestHeader String token) {
        log.info("getSecFormLatest");
        return secFeign.getSecFormLatest(token);
    }

    @ApiOperation(value = "ID删除专家考核评价表")
    @GetMapping("/deleteSecFormById")
    public BaseResponse deleteSecFormById(@RequestParam String id) {
        log.info("deleteSecFormById");
        return secFeign.deleteSecFormById(id);
    }
}
