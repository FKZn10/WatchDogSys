package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProCplTemplateRequest;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "建设程序项目模板", value = "建设程序项目模板")
@RequestMapping("/v1/ConstructionProcedure/proCplTemplate")
public class ProjectCplTemplateController {
    @Autowired
    private CpFeign cpFeign;


    /**
     * 新增或者修改 建设程序项目模板
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或者修改 建设程序项目模板", httpMethod = "POST")
    @PostMapping("/createOrUpdateProCplTemplate")
    public BaseResponse createOrUpdateProCplTemplate(@RequestBody ProCplTemplateRequest request) {
        log.info("createOrUpdateProCplTemplate");
        return cpFeign.createOrUpdateProCplTemplate(request);
    }

    /**
     * 获取建设程序项目模板集合
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取建设程序项目模板集合", httpMethod = "POST")
    @PostMapping("/getProCplTemplateList")
    public BaseResponse getProCplTemplateList(@RequestBody ProCplTemplateRequest request) {
        log.info("getProCplTemplateList");
        return cpFeign.getProCplTemplateList(request);
    }

    /**
     * 删除
     *
     * @param cpltId
     * @return
     */
    @ApiOperation(value = "删除建设程序项目模板")
    @GetMapping("/deleteProCplTemplateById")
    public BaseResponse deleteProCplTemplateById(@RequestParam String cpltId) {
        log.info("deleteProCplTemplateById");
        return cpFeign.deleteProCplTemplateById(cpltId);
    }
}
