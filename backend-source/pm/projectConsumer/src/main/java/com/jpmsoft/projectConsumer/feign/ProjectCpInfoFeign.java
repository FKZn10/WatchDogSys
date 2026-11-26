package com.jpmsoft.projectConsumer.feign;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.ProjectCpDtl;
import com.jpmsoft.projectConsumer.request.ProjectCpInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "project-info")
public interface ProjectCpInfoFeign {

    @GetMapping("v1/projectCpInfo/handleProjectCpInfo")
    BaseResponse dealWith(@RequestParam("id") String id,@RequestParam(value ="type",required = false) String type);

    @GetMapping("v1/projectCpInfo/cancelProjectCpInfo")
    BaseResponse cancelProjectCpInfo(@RequestParam("cpdId") String cpdId, @RequestParam("cprId") String cprId);

    @PostMapping("v1/projectCpInfo/createProjectCpInfo")
    BaseResponse createProjectCpInfo(@RequestBody ProjectCpDtl projectCpDtl);

    @PostMapping("v1/projectCpInfo/getProjectInfo")
    BaseResponse getProjectInfo(@RequestBody ProjectCpInfoRequest projectCpInfoRequest, @RequestHeader String token);
}

