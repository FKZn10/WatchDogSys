package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertAppealDTO;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertAppealDtlDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 14:08
 */
@Slf4j
@RestController
@Api(tags = "专家考核申诉", value = "专家考核申诉")
@RequestMapping("/v1/expert/appeal")
public class SecExpertAppealController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家考核申诉")
    @PostMapping("/createOrUpdateSecExpertAppeal")
    public BaseResponse createOrUpdateSecExpertAppeal(@RequestBody SecExpertAppealDTO request) {
        log.info("createOrUpdateSecExpertAppeal");
        return secFeign.createOrUpdateSecExpertAppeal(request);
    }

    @ApiOperation(value = "分页查询专家考核申诉")
    @PostMapping("/getSecExpertAppealList")
    public BaseResponse getSecExpertAppealList(@RequestBody SecExpertAppealDTO request, @RequestHeader String token) {
        log.info("getSecExpertAppealList");
        return secFeign.getSecExpertAppealList(request, token);
    }

    @ApiOperation(value = "ID查询专家考核申诉")
    @GetMapping("/getSecExpertAppealById")
    public BaseResponse getSecExpertAppealById(@RequestParam String id) {
        log.info("getSecExpertAppealById");
        return secFeign.getSecExpertAppealById(id);
    }

    @ApiOperation(value = "审核通过后同步同意申诉的专家评分考核内容")
    @GetMapping("/approveSecExpertAppealById")
    public BaseResponse approveSecExpertAppealById(@RequestParam String id) {
        log.info("approveSecExpertAppealById");
        return secFeign.approveSecExpertAppealById(id);
    }

    @ApiOperation(value = "修改考核申诉内容信息")
    @PostMapping("/updateSecExpertAppealDtl")
    public BaseResponse updateSecExpertAppealDtl(@RequestBody SecExpertAppealDtlDTO request) {
        log.info("updateSecExpertAppealDtl");
        return secFeign.updateSecExpertAppealDtl(request);
    }

    @ApiOperation(value = "修改考核申诉内容信息")
    @PostMapping("/updateSecExpertAppealDtlBath")
    public BaseResponse updateSecExpertAppealDtlBath(@RequestBody List<SecExpertAppealDtlDTO> request) {
        log.info("updateSecExpertAppealDtlBath");
        return secFeign.updateSecExpertAppealDtlBath(request);
    }
}
