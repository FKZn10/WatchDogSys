package com.jpmsoft.projectProducer.controller.v1.projectKanban;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.projectKanban.projectFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/projectFund")
public class ProjectFundController {

    @Autowired
    private projectFundService projectFundService;

    @GetMapping("/getProjectFund")
    public BaseResponse getProjectFund(@RequestParam String pId) {
        return projectFundService.getProjectFund(pId);
    }
}
