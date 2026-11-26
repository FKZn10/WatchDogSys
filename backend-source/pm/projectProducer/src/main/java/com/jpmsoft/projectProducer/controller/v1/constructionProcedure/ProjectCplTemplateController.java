package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProCplTemplateRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCplTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCplTemplate")
public class ProjectCplTemplateController {

    @Resource
    private IProjectCplTemplateService projectCplTemplateService;


    /**
     * 新增或者修改 建设程序项目模板
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateProCplTemplate")
    public BaseResponse createOrUpdateProCplTemplate(@RequestBody ProCplTemplateRequest request) {
        log.info("createOrUpdateProCplTemplate");
        return projectCplTemplateService.createOrUpdateProCplTemplate(request);
    }

    /**
     * 获取集合
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProCplTemplateList")
    public BaseResponse getProCplTemplateList(@RequestBody ProCplTemplateRequest request) {
        log.info("getProCplTemplateList");
        return projectCplTemplateService.getProCplTemplateList(request);
    }

    /**
     * 删除
     *
     * @param cpltId
     * @return
     */
    @GetMapping("/deleteProCplTemplateById")
    public BaseResponse deleteProCplTemplateById(@RequestParam String cpltId) {
        log.info("deleteProCplTemplateById");
        return projectCplTemplateService.deleteProCplTemplateById(cpltId);
    }
}
