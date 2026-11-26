package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ProjectCpInfoFeign;
import com.jpmsoft.projectConsumer.pojo.ProjectCpDtl;
import com.jpmsoft.projectConsumer.request.ProjectCpInfoRequest;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("v1/projectCpInfo")
@Api(tags = "项目程序-新增，取消，修改，查询程序", value = "项目程序-新增，取消，修改，查询程序")
public class ProjectCpInfoController {
    @Autowired
    ProjectCpInfoFeign feign;

    /**
     * Project_Cp_Rpr成果办理的id
     * @param id
     * @return
     */
    @GetMapping("/handleProjectCpInfo")
    @ApiOperation(value = "根据成果表的主键id修改项目程序的状态isEnd和State->流程状态设置为7")
    public BaseResponse handleProjectCpInfo(@RequestParam("id") String id,@RequestParam(value ="type",required = false) String type){
        return feign.dealWith(id,type);
    }

    @GetMapping("/cancelProjectCpInfo")
    @ApiOperation(value = "根据前端的程序详情的id将Status设置为0，向上向下递归")
    public BaseResponse cancelProjectCpInfo(@RequestParam("cpdId") String cpdId, @RequestParam("cprId") String cprId){
        return feign.cancelProjectCpInfo(cpdId, cprId);
    }

    @PostMapping("/createProjectCpInfo")
    @ApiOperation(value = "创建程序及其父程序")
    public BaseResponse createProjectCpInfo(@RequestBody ProjectCpDtl projectCpDtl){
        return feign.createProjectCpInfo(projectCpDtl);
    }

    @PostMapping("/getProjectInfo")
    @ApiOperation(value = "获取projectInfo")
    public BaseResponse getProjectInfo(@RequestBody ProjectCpInfoRequest projectCpInfoRequest, @RequestHeader String token){
        return feign.getProjectInfo(projectCpInfoRequest,token);
    }
}
