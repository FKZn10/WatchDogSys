package com.jpmsoft.projectConsumer.controller.v1.cockpit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.ProjectPanoramaOverviewModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目全景及工程概况", value = "项目全景及工程概况")
@RequestMapping("/SmartSite/v1/proPanorama")
public class ProjectPanoramaOverviewController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改项目全景及工程概况")
    @PostMapping("/createOrUpdateProjectPanoramaOverview")
    public BaseResponse createOrUpdateProjectPanoramaOverview(@RequestBody ProjectPanoramaOverviewModel request) {
        log.info("createOrUpdateProjectPanoramaOverview");
        return baseFeign.createOrUpdateProjectPanoramaOverview(request);
    }

    @ApiOperation(value = "分页项目全景及工程概况")
    @PostMapping("/getProjectPanoramaOverviewList")
    public BaseResponse getProjectPanoramaOverviewList(@RequestBody ProjectPanoramaOverviewModel request, @RequestHeader String token) {
        log.info("getProjectPanoramaOverviewList");
        return baseFeign.getProjectPanoramaOverviewList(request, token);
    }

    @PostMapping("/getProjectPanoramaList")
    public BaseResponse getProjectPanoramaList(@RequestHeader String token) {
        log.info("getProjectPanoramaList");
        return baseFeign.getProjectPanoramaList(token);
    }

    @ApiOperation(value = "ID查询项目全景及工程概况")
    @GetMapping("/getProjectPanoramaOverviewById")
    public BaseResponse getProjectPanoramaOverviewById(@RequestParam String ppoId) {
        log.info("getProjectPanoramaOverviewById");
        return baseFeign.getProjectPanoramaOverviewById(ppoId);
    }

    @ApiOperation(value = "ID删除")
    @GetMapping("/deleteProjectPanoramaOverviewById")
    public BaseResponse deleteProjectPanoramaOverviewById(@RequestParam String ppoId) {
        log.info("deleteProjectPanoramaOverviewById");
        return baseFeign.deleteProjectPanoramaOverviewById(ppoId);
    }

    /**
     * 获取图片地址用于轮播图
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取图片地址用于轮播图")
    @PostMapping("/getProjectPanoramaOverviewFile")
    public BaseResponse getProjectPanoramaOverviewFile(@RequestBody ProjectPanoramaOverviewModel request) {
        log.info("getProjectPanoramaOverviewFile");
        return baseFeign.getProjectPanoramaOverviewFile(request);
    }
}
