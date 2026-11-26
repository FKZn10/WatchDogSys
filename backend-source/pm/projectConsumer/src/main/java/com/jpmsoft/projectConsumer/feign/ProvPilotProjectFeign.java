package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 省试点项目 Feign
 *
 * @author whjp
 * @since 2026-09-16
 */
@FeignClient(name = "project-producer", contextId = "provPilotProject")
public interface ProvPilotProjectFeign {

    @PostMapping("/v1/provPilotProject/createOrUpdate")
    BaseResponse createOrUpdate(@RequestBody ProvPilotProjectModel request, @RequestHeader String token);

    @PostMapping("/v1/provPilotProject/getList")
    BaseResponse getList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token);

    @GetMapping("/v1/provPilotProject/getById")
    BaseResponse getById(@RequestParam("id") String id);

    @PostMapping("/v1/provPilotProject/deleteBatch")
    BaseResponse deleteBatch(@RequestBody List<String> ids);

    @PostMapping("/v1/provPilotProject/importProvPilotProject")
    BaseResponse importProvPilotProject(@RequestBody ProvPilotProjectImportRequest request, @RequestHeader String token);

    @PostMapping("/v1/provPilotProject/getExportList")
    List<ProvPilotProjectModel> getExportList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token);
}