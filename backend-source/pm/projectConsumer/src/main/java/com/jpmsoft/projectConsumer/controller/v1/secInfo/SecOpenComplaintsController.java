package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecOpenComplaintsDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 14:13
 */
@Slf4j
@RestController
@Api(tags = "开评标质疑投诉处理", value = "开评标质疑投诉处理")
@RequestMapping("/v1/openComplaints")
public class SecOpenComplaintsController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改开评标质疑投诉处理")
    @PostMapping("/createOrUpdateSecOpenComplaints")
    public BaseResponse createOrUpdateSecOpenComplaints(@RequestBody SecOpenComplaintsDto request) {
        log.info("createOrUpdateSecOpenComplaints");
        return secFeign.createOrUpdateSecOpenComplaints(request);
    }

    @ApiOperation(value = "分页查询开评标质疑投诉处理")
    @PostMapping("/getSecOpenComplaintsList")
    public BaseResponse getSecOpenComplaintsList(@RequestBody SecOpenComplaintsDto request) {
        log.info("getSecOpenComplaintsList");
        return secFeign.getSecOpenComplaintsList(request);
    }

    @ApiOperation(value = "ID查询开评标质疑投诉处理")
    @GetMapping("/getSecOpenComplaintsById")
    public BaseResponse getSecOpenComplaintsById(@RequestParam String id) {
        log.info("getSecOpenComplaintsById");
        return secFeign.getSecOpenComplaintsById(id);
    }
}
