package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 项目建设程序库-风险点防控措施关联表 控制器
 */
@Slf4j
@RestController
@Api(value = "项目建设程序库", tags = "项目建设程序库")
@RequestMapping("/v1/projectCpLibraryRisk")
public class ProjectCpLibraryRiskController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 根据 cplId 获取项目建设程序库-风险点防控措施关联表 列表
     *
     * @param cplId
     * @return
     */
    @ApiOperation("根据cplId获取项目建设程序库-风险点防控措施关联表列表")
    @GetMapping("/getProjectCpLibraryRiskByCplId")
    public BaseResponse getProjectCpLibraryRiskByCplId(@RequestParam String cplId) {
        log.info("getProjectCpLibraryRiskByCplId");
        return baseFeign.getProjectCpLibraryRiskByCplId(cplId);
    }
}