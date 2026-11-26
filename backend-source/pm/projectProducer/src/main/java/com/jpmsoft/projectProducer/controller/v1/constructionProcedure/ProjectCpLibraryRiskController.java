package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpLibraryRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 项目建设程序库-风险点防控措施关联表 控制器
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectCpLibraryRisk")
public class ProjectCpLibraryRiskController {
    @Resource
    private IProjectCpLibraryRiskService projectCpLibraryRiskService;

    /**
     * 根据 cplId 获取项目建设程序库-风险点防控措施关联表 列表
     *
     * @param cplId
     * @return
     */
    @GetMapping("/getProjectCpLibraryRiskByCplId")
    public BaseResponse getProjectCpLibraryRiskByCplId(String cplId) {
        log.info("getProjectCpLibraryRiskByCplId");
        return BaseResponse.ok(projectCpLibraryRiskService.getProjectCpLibraryRiskByCplId(cplId));
    }
}
