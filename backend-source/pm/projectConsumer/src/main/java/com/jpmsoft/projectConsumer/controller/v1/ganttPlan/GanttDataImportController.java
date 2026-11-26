package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "进度里程碑统计", value = "进度里程碑统计")
@RequestMapping("/plan/v1/ganttDataImport")
@AllArgsConstructor
public class GanttDataImportController {

    private final BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改进度里程碑统计")
    @GetMapping("/importData")
    public BaseResponse importData(){
        log.info("importData");
        return baseFeign.importData();
    }
}
