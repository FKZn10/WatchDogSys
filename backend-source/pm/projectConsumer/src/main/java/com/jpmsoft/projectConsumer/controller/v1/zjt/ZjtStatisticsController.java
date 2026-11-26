package com.jpmsoft.projectConsumer.controller.v1.zjt;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/zjt/zjtStatistics")
@Api(tags = "住建厅统计，填报统计", value = "住建厅统计，填报统计")
@Slf4j
public class ZjtStatisticsController {
    @Autowired
    private BaseFeign baseFeign;

    @PostMapping("/getProjectUpStatistics")
    public BaseResponse getProjectUpStatistics(@RequestBody ProjectUpStatisticsDTO request) {
        log.info("getProjectUpStatistics");
        return baseFeign.getProjectUpStatistics(request);
    }

    @PostMapping("/selectExportProjectData")
    public BaseResponse selectExportProjectData(@RequestBody Object request, @RequestHeader String token) {
        log.info("selectExportProjectData");
        return baseFeign.selectExportProjectData(request, token);
    }
}
