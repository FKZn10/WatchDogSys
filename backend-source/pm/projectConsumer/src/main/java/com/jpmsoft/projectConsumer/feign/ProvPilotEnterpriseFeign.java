package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 省试点企业 Feign
 *
 * @author whjp
 * @since 2026-09-17
 */
@FeignClient(name = "project-producer", contextId = "provPilotEnterprise")
public interface ProvPilotEnterpriseFeign {

    @PostMapping("/v1/provPilotEnterprise/createOrUpdate")
    BaseResponse createOrUpdate(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token);

    @PostMapping("/v1/provPilotEnterprise/getList")
    BaseResponse getList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token);

    @GetMapping("/v1/provPilotEnterprise/getById")
    BaseResponse getById(@RequestParam("id") String id);

    @PostMapping("/v1/provPilotEnterprise/deleteBatch")
    BaseResponse deleteBatch(@RequestBody List<String> ids);

    @PostMapping("/v1/provPilotEnterprise/importProvPilotEnterprise")
    BaseResponse importProvPilotEnterprise(@RequestBody ProvPilotEnterpriseImportRequest request, @RequestHeader String token);

    @PostMapping("/v1/provPilotEnterprise/getExportList")
    List<ProvPilotEnterpriseModel> getExportList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token);

    /**
     * 查询全部年度的试点企业卡片，评估及验收尚无数据来源时标记为不可用。
     *
     * @param token 登录令牌，用于取得租户范围
     * @return 企业总数及评估、验收可用性
     */
    @PostMapping("/v1/provPilotEnterprise/getEnterpriseStatistics")
    BaseResponse getEnterpriseStatistics(@RequestHeader("token") String token);
}