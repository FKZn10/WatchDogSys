package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "建设程序项目模板详情", value = "建设程序项目模板详情")
@RequestMapping("/v1/ConstructionProcedure/proCplTemplateDtl")
public class ProjectCplTemplateDtlController {
    @Autowired
    private CpFeign cpFeign;

    /**
     * 获取建设程序项目模板详情树
     *
     * @param cpltId
     * @return
     */
    @ApiOperation(value = "获取建设程序项目模板详情树")
    @GetMapping("/getProCplTemplateDtlTreeByCpltId")
    public BaseResponse getProCplTemplateDtlTreeByCpltId(@RequestParam String cpltId) {
        log.info("getProCplTemplateDtlTreeByCpltId");
        return cpFeign.getProCplTemplateDtlTreeByCpltId(cpltId);
    }

    @ApiOperation(value = "获取建设程序项目模板详情ID集合")
    @GetMapping("/getProCplTemplateDtlIdListByCpltId")
    public BaseResponse getProCplTemplateDtlIdListByCpltId(@RequestParam String cpltId) {
        log.info("getProCplTemplateDtlIdListByCpltId");
        return cpFeign.getProCplTemplateDtlIdListByCpltId(cpltId);
    }

    @ApiOperation(value = "模板详情ID删除模板详情")
    @GetMapping("/deleteProCplTemplateDtlByCpltdId")
    public BaseResponse deleteProCplTemplateDtlByCpltdId(@RequestParam String cpltdId) {
        log.info("deleteProCplTemplateDtlByCpltdId");
        cpFeign.deleteProCplTemplateDtlByCpltdId(cpltdId);
        return new BaseResponse(true, "");
    }
}
