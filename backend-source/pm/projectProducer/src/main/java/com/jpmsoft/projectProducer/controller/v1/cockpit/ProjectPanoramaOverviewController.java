package com.jpmsoft.projectProducer.controller.v1.cockpit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.ProjectPanoramaOverviewModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.cockpit.IProjectPanoramaOverviewService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/SmartSite/v1/proPanorama")
public class ProjectPanoramaOverviewController {

    @Resource
    private IProjectPanoramaOverviewService projectPanoramaOverviewService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateProjectPanoramaOverview")
    public BaseResponse createOrUpdateProjectPanoramaOverview(@RequestBody ProjectPanoramaOverviewModel request) {
        log.info("createOrUpdateProjectPanoramaOverview");
        return projectPanoramaOverviewService.createOrUpdateProjectPanoramaOverview(request);
    }

    @TenantId
    @PostMapping("/getProjectPanoramaOverviewList")
    public BaseResponse getProjectPanoramaOverviewList(@RequestBody ProjectPanoramaOverviewModel request, @RequestHeader String token) {
        log.info("getProjectPanoramaOverviewList");
        return projectPanoramaOverviewService.getProjectPanoramaOverviewList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getProjectPanoramaList")
    public BaseResponse getProjectPanoramaList(@RequestHeader String token) {
        log.info("getProjectPanoramaList");
        return projectPanoramaOverviewService.getProjectPanoramaList(redisUtils.getTeId(token));
    }


    @GetMapping("/getProjectPanoramaOverviewById")
    public BaseResponse getProjectPanoramaOverviewById(@RequestParam String ppoId) {
        log.info("getProjectPanoramaOverviewById");
        return projectPanoramaOverviewService.getProjectPanoramaOverviewById(ppoId);
    }

    @GetMapping("/deleteProjectPanoramaOverviewById")
    public BaseResponse deleteProjectPanoramaOverviewById(@RequestParam String ppoId) {
        log.info("deleteProjectPanoramaOverviewById");
        return projectPanoramaOverviewService.deleteProjectPanoramaOverviewById(ppoId);
    }

    /**
     * 获取图片地址用于轮播图
     *
     * @param request
     * @return
     */
    @PostMapping("/getProjectPanoramaOverviewFile")
    public BaseResponse getProjectPanoramaOverviewFile(@RequestBody ProjectPanoramaOverviewModel request) {
        log.info("getProjectPanoramaOverviewFile");
        return projectPanoramaOverviewService.getProjectPanoramaOverviewFile(request);
    }
}
