package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
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
@Api(tags = "专家请假申请", value = "专家请假申请")
@RequestMapping("/v1/expert/vacation")
public class SecExpertVacationController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家请假申请")
    @PostMapping("/createOrUpdateSecExpertVacation")
    public BaseResponse createOrUpdateSecExpertVacation(@RequestBody SecExpertVacationDTO request) {
        log.info("createOrUpdateSecExpertVacation");
        return secFeign.createOrUpdateSecExpertVacation(request);
    }

    @ApiOperation(value = "分页查询专家请假申请")
    @PostMapping("/getSecExpertVacationList")
    public BaseResponse getSecExpertVacationList(@RequestBody SecExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecExpertVacationList");
        return secFeign.getSecExpertVacationList(request, token);
    }

    @ApiOperation(value = "ID查询专家请假申请")
    @GetMapping("/getSecExpertVacationById")
    public BaseResponse getSecExpertVacationById(@RequestParam String id) {
        log.info("getSecExpertVacationById");
        return secFeign.getSecExpertVacationById(id);
    }

    @ApiOperation(value = "更新是否按时参加，是否请假标记、生成专家补抽以及待办.")
    @GetMapping("/approveSecExpertVacationById")
    public BaseResponse approveSecExpertVacationById(@RequestParam String id) {
        log.info("approveSecExpertVacationById");
        return secFeign.approveSecExpertVacationById(id);
    }

}
