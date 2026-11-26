package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecFormDetailDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:53
 */
@Slf4j
@RestController
@Api(tags = "专家库-考核评价表详细", value = "专家库-考核评价表详细")
@RequestMapping("/v1/expert/secFormDetail")
public class SecFormDetailController {

    @Resource
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改专家考核评价表详细")
    @PostMapping("/createOrUpdateSecFormDetail")
    public BaseResponse createOrUpdateSecFormDetail(@RequestBody SecFormDetailDTO request) {
        log.info("createOrUpdateSecFormDetail");
        return secFeign.createOrUpdateSecFormDetail(request);
    }

    @ApiOperation(value = "分页查询专家考核评价表详细")
    @PostMapping("/getSecFormDetailList")
    public BaseResponse getSecFormDetailList(@RequestBody SecFormDetailDTO request) {
        log.info("getSecFormDetailList");
        return secFeign.getSecFormDetailList(request);
    }

    @ApiOperation(value = "ID删除专家考核评价表详细")
    @GetMapping("/deleteSecFormDetailById")
    public BaseResponse deleteSecFormDetailById(@RequestParam String id) {
        log.info("deleteSecFormDetailById");
        return secFeign.deleteSecFormDetailById(id);
    }

    @ApiOperation(value = "查询最新可用的专家考核评价表详细列表")
    @GetMapping("/getSecFormDetailLatestList")
    public BaseResponse getSecFormDetailLatestList() {
        log.info("getSecFormDetailLatestList");
        return secFeign.getSecFormDetailLatestList();
    }
}
