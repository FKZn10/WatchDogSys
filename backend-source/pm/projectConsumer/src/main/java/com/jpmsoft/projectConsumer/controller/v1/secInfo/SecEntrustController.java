package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecEntrustDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/31 11:22
 */
@Slf4j
@RestController
@Api(tags = "直接委托", value = "直接委托")
@RequestMapping("/v1/entrust")
public class SecEntrustController {

    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改直接委托")
    @PostMapping("/createOrUpdateSecEntrust")
    public BaseResponse createOrUpdateSecEntrust(@RequestBody SecEntrustDto request) {
        log.info("createOrUpdateSecEntrust");
        return secFeign.createOrUpdateSecEntrust(request);
    }

    @ApiOperation(value = "ID查询直接委托")
    @GetMapping("/getSecEntrustById")
    public BaseResponse getSecEntrustById(@RequestParam String id) {
        log.info("getSecEntrustById");
        return secFeign.getSecEntrustById(id);
    }

    @ApiOperation(value = "分页查询直接委托")
    @PostMapping("/getSecEntrustList")
    public BaseResponse getSecEntrustList(@RequestBody SecEntrustDto request) {
        log.info("getSecEntrustList");
        return secFeign.getSecEntrustList(request);
    }
}
