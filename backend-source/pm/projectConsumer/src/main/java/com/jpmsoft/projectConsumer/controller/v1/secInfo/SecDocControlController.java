package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecDocControlDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 招标文件控制价
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:50
 */
@Slf4j
@RestController
@Api(tags = "招标文件控制价", value = "招标文件控制价")
@RequestMapping("/v1/docControl")
public class SecDocControlController {

    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标文件控制价")
    @PostMapping("/createOrUpdateSecDocControl")
    public BaseResponse createOrUpdateSecDocControl(@RequestBody SecDocControlDto request) {
        log.info("createOrUpdateSecDocControl");
        return secFeign.createOrUpdateSecDocControl(request);
    }

    @ApiOperation(value = "分页查询招标文件控制价")
    @PostMapping("/getSecDocControlList")
    public BaseResponse getSecDocControlList(@RequestBody SecDocControlDto request) {
        log.info("getSecDocControlList");
        return secFeign.getSecDocControlList(request);
    }

    @ApiOperation(value = "ID查询改招标文件控制价")
    @GetMapping("/getSecDocControlById")
    public BaseResponse getSecDocControlById(@RequestParam String id) {
        log.info("getSecDocControlById");
        return secFeign.getSecDocControlById(id);
    }
}
