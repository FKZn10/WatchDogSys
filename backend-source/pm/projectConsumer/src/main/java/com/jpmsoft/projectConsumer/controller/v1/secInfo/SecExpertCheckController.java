package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertCheckDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 14:08
 */
@Slf4j
@RestController
@Api(tags = "专家考核评分", value = "专家考核评分")
@RequestMapping("/v1/expert/check")
public class SecExpertCheckController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家考核评分")
    @PostMapping("/createOrUpdateSecExpertCheck")
    public BaseResponse createOrUpdateSecExpertCheck(@RequestBody SecExpertCheckDTO request) {
        log.info("createOrUpdateSecExpertCheck");
        return secFeign.createOrUpdateSecExpertCheck(request);
    }

    @ApiOperation(value = "分页查询专家考核评分")
    @PostMapping("/getSecExpertCheckList")
    public BaseResponse getSecExpertCheckList(@RequestBody SecExpertCheckDTO request, @RequestHeader String token) {
        log.info("getSecExpertCheckList");
        return secFeign.getSecExpertCheckList(request, token);
    }

    @ApiOperation(value = "ID查询专家考核评分")
    @GetMapping("/getSecExpertCheckById")
    public BaseResponse getSecExpertCheckById(@RequestParam String id) {
        log.info("getSecExpertCheckById");
        return secFeign.getSecExpertCheckById(id);
    }

    @ApiOperation(value = "获取当前登录人考核评分被扣分的记录")
    @GetMapping("/getExpertCheckDtlList")
    public BaseResponse getExpertCheckDtlList(@RequestParam String secId,@RequestHeader String token)  {
        log.info("getExpertCheckDtlList");
        return secFeign.getExpertCheckDtlList(secId,token);
    }

    @GetMapping("getSecExpertCheckBySrId")
    public BaseResponse getSecExpertCheckBySrId(@RequestParam String srId,@RequestHeader String token) {
        log.info("getSecExpertCheckBySrId");
        return secFeign.getSecExpertCheckBySrId(srId, token);
    }

    @GetMapping("getSecEXpertCheckSendToDo")
    public BaseResponse getSecEXpertCheckSendToDo() {
        log.info("getSecEXpertCheckSendToDo");
        return secFeign.getSecEXpertCheckSendToDo();
    }

    @ApiOperation("一标一评评分同步专家停用记录")
    @GetMapping("/syncSecExpertPause")
    public BaseResponse syncSecExpertPause(@RequestParam String id) {
        log.info("syncSecExpertPause");
        return secFeign.syncSecExpertPause(id);
    }
}
