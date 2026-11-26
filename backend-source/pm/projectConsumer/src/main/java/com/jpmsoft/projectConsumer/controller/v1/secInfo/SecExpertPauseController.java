package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertPauseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:25
 */
@Slf4j
@RestController
@Api(tags = "专家暂停申请", value = "专家暂停申请")
@RequestMapping("/v1/expert/pause")
public class SecExpertPauseController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家暂停申请")
    @PostMapping("/createOrUpdateSecExpertPause")
    public BaseResponse createOrUpdateSecExpertPause(@RequestBody SecExpertPauseDTO request) {
        log.info("createOrUpdateSecExpertPause");
        return secFeign.createOrUpdateSecExpertPause(request);
    }

    @ApiOperation(value = "分页查询专家暂停申请")
    @PostMapping("/getSecExpertPauseList")
    public BaseResponse getSecExpertPauseList(@RequestBody SecExpertPauseDTO request, @RequestHeader String token) {
        log.info("getSecExpertPauseList");
        return secFeign.getSecExpertPauseList(request, token);
    }

    @ApiOperation(value = "专家暂停申请的查询条件")
    @PostMapping("/getSecExpertPauseListClass")
    public BaseResponse getSecExpertPauseListClass(@RequestBody SecExpertPauseDTO request, @RequestHeader String token) {
        log.info("getSecExpertPauseListClass");
        return secFeign.getSecExpertPauseListClass(request, token);
    }

    @ApiOperation(value = "ID查询专家暂停申请")
    @GetMapping("/getSecExpertPauseById")
    public BaseResponse getSecExpertPauseById(@RequestParam String id) {
        log.info("getSecExpertPauseById");
        return secFeign.getSecExpertPauseById(id);
    }

    @ApiOperation(value = "请假申请审批通过暂停专家（年度内请假天数超过配置值则停用专家）")
    @GetMapping("/approveSecExpertPauseById")
    public BaseResponse approveSecExpertPauseById(@RequestParam String id) {
        log.info("approveSecExpertPauseById");
        return secFeign.approveSecExpertPauseById(id);
    }

    @ApiOperation(value = "定时任务审批通过暂停专家")
    @GetMapping("/approveSecExpertPause")
    public BaseResponse approveSecExpertPause() {
        log.info("approveSecExpertPause");
        return secFeign.approveSecExpertPause();
    }
}
