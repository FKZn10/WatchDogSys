package com.jpmsoft.projectProducer.controller.v1.zjt;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.zjt.ZjtProjectExportDTO;
import com.jpmsoft.projectProducer.service.zjt.ZjtStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/zjt/zjtStatistics")
@Slf4j
public class ZjtStatisticsController {

    @Resource
    private ZjtStatisticsService zjtStatisticsService;

    @PostMapping("/getProjectUpStatistics")
    public BaseResponse getProjectUpStatistics(@RequestBody ProjectUpStatisticsDTO request) {
        log.info("getProjectUpStatistics");
        return zjtStatisticsService.getProjectUpStatistics(request);
    }

    @PostMapping("/selectExportProjectData")
    public BaseResponse selectExportProjectData(@RequestBody ZjtProjectExportDTO request, @RequestHeader String token) {
        log.info("selectExportProjectData");
        return zjtStatisticsService.exportProject(request, token);
    }

}
