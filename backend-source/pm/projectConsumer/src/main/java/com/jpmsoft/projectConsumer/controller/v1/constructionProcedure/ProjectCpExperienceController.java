package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpExperienceRequest;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "经验分享", value = "经验分享")
@RequestMapping("/v1/ConstructionProcedure/proCpExperience")
public class ProjectCpExperienceController {
    @Autowired
    private CpFeign cpFeign;

    @ApiOperation(value = "创建或修改经验分享")
    @PostMapping("/createOrUpdateProCpExperience")
    public BaseResponse createOrUpdateProCpExperience(@RequestBody ProjectCpExperienceRequest request) {
        log.info("createOrUpdateProCpExperience");
        return cpFeign.createOrUpdateProCpExperience(request);
    }

    @ApiOperation(value = "ID删除经验分享")
    @GetMapping("/deleteProCpExperienceById")
    public BaseResponse deleteProCpExperienceById(@RequestParam String cpeId) {
        log.info("deleteProCpExperienceById");
        return cpFeign.deleteProCpExperienceById(cpeId);
    }

    @ApiOperation(value = "获取经验分享列表")
    @PostMapping("/getProCpExperienceList")
    public BaseResponse getProCpExperienceList(@RequestBody ProjectCpExperienceRequest request) {
        log.info("getProCpExperienceList");
        return cpFeign.getProCpExperienceList(request);
    }
}
