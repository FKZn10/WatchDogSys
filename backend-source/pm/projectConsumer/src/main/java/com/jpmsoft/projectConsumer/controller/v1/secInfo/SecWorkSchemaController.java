package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 10:05
 */
@Slf4j
@RestController
@Api(tags = "招标工作方案", value = "招标工作方案")
@RequestMapping("/v1/workSchema")
public class SecWorkSchemaController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标工作方案")
    @PostMapping("/createOrUpdateSecWorkSchema")
    public BaseResponse createOrUpdateSecWorkSchema(@RequestBody SecWorkSchemaDTO request) {
        log.info("createOrUpdateSecWorkSchema");
        return secFeign.createOrUpdateSecWorkSchema(request);
    }
    @ApiOperation(value = "分页查询招标工作方案 - 多条件筛选")
    @PostMapping("/getSecWorkSchemaListClassInfo")
    public BaseResponse getSecWorkSchemaListClassInfo(@RequestBody SecWorkSchemaDTO request,@RequestHeader String token) {
        log.info("getSecWorkSchemaListClassInfo");
        return secFeign.getSecWorkSchemaListClassInfo(request,token);
    }


    @PostMapping("/getSecWorkSchemaListClass")
    public BaseResponse getSecWorkSchemaListClass(@RequestBody SecWorkSchemaDTO request,@RequestHeader String token) {
        log.info("getSecWorkSchemaListClass");
        return secFeign.getSecWorkSchemaListClass(request,token);
    }

    @ApiOperation(value = "分页查询招标工作方案")
    @PostMapping("/getSecWorkSchemaList")
    public BaseResponse getSecWorkSchemaList(@RequestBody SecWorkSchemaDTO request) {
        log.info("getSecWorkSchemaList");
        return secFeign.getSecWorkSchemaList(request);
    }

    @ApiOperation(value = "ID查询")
    @GetMapping("/getSecWorkSchemaByWsId")
    public BaseResponse getSecWorkSchemaByWsId(@RequestParam String wsId) {
        log.info("getSecWorkSchemaByWsId");
        return secFeign.getSecWorkSchemaByWsId(wsId);
    }

    @ApiOperation(value = "标段ID查询招标工作方案")
    @GetMapping("/getSecWorkSchemaBySecId")
    public BaseResponse getSecWorkSchemaBySecId(@RequestParam String secId) {
        log.info("getSecWorkSchemaBySecId");
        return secFeign.getSecWorkSchemaBySecId(secId);
    }
}
