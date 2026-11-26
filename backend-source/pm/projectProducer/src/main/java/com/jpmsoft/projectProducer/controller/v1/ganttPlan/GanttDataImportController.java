package com.jpmsoft.projectProducer.controller.v1.ganttPlan;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttDataImportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/ganttDataImport")
@AllArgsConstructor
public class GanttDataImportController {

    private final GanttDataImportService ganttDataImportService;

    @GetMapping("/importData")
    public BaseResponse importData(){
        log.info("importData");
        return ganttDataImportService.dataImport();
    }
}
