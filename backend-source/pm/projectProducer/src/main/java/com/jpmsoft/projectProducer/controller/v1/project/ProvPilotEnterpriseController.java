package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.project.IProvPilotEnterpriseService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省试点企业 Controller（生产端 PP）
 *
 * @author whjp
 * @since 2026-09-17
 */
@Slf4j
@RestController
@RequestMapping("/v1/provPilotEnterprise")
public class ProvPilotEnterpriseController {

    @Resource
    private IProvPilotEnterpriseService provPilotEnterpriseService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return provPilotEnterpriseService.createOrUpdate(request, user);
    }

    @TenantId
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token) {
        return provPilotEnterpriseService.getList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam("id") String id) {
        return provPilotEnterpriseService.getById(id);
    }

    @PostMapping("/deleteBatch")
    public BaseResponse deleteBatch(@RequestBody List<String> ids) {
        return provPilotEnterpriseService.deleteBatch(ids);
    }

    @PostMapping("/importProvPilotEnterprise")
    public BaseResponse importProvPilotEnterprise(@RequestBody ProvPilotEnterpriseImportRequest request, @RequestHeader String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return provPilotEnterpriseService.importProvPilotEnterprise(request, user);
    }

    @TenantId
    @PostMapping("/getExportList")
    public List<ProvPilotEnterpriseModel> getExportList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token) {
        return provPilotEnterpriseService.getExportList(request, redisUtils.getUid(token));
    }

    /**
     * 查询全部年度的试点企业卡片，评估及验收尚无数据来源时标记为不可用。
     *
     * @param token 登录令牌，用于取得租户范围
     * @return 企业总数及评估、验收可用性
     */
    @PostMapping("/getEnterpriseStatistics")
    public BaseResponse getEnterpriseStatistics(@RequestHeader("token") String token) {
        return provPilotEnterpriseService.getEnterpriseStatistics(redisUtils.getUserInfoByToken(token));
    }
}