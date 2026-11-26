package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCplTemplateDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCplTemplateDtl")
public class ProjectCplTemplateDtlController {
    @Resource
    private IProjectCplTemplateDtlService projectCplTemplateDtlService;

    /**
     * 获取建设程序项目模板详情树
     *
     * @param cpltId
     * @return
     */
    @GetMapping("/getProCplTemplateDtlTreeByCpltId")
    public BaseResponse getProCplTemplateDtlTreeByCpltId(@RequestParam String cpltId) {
        log.info("getProCplTemplateDtlTreeByCpltId");
        return projectCplTemplateDtlService.getProCplTemplateDtlTreeByCpltId(cpltId);
    }

    @GetMapping("/getProCplTemplateDtlIdListByCpltId")
    public BaseResponse getProCplTemplateDtlIdListByCpltId(@RequestParam String cpltId) {
        log.info("getProCplTemplateDtlIdListByCpltId");
        return projectCplTemplateDtlService.getProCplTemplateDtlIdListByCpltId(cpltId);
    }

    @Deprecated
    @GetMapping("/deleteProCplTemplateDtlById")
    public BaseResponse deleteProCplTemplateDtlById(@RequestParam String id) {
        log.info("getProCplTemplateDtlIdListByCpltId");
        projectCplTemplateDtlService.deleteProCplTemplateDtlById(id);
        return BaseResponse.ok();
    }

    @Deprecated
    @GetMapping("/deleteProCplTemplateDtlByCpltdId")
    public BaseResponse deleteProCplTemplateDtlByCpltdId(@RequestParam String cpltdId) {
        log.info("deleteProCplTemplateDtlByCpltdId");
        projectCplTemplateDtlService.deleteProCplTemplateDtlByCpltdId(cpltdId);
        return BaseResponse.ok();
    }
}
