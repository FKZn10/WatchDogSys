package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.request.ProjectCpInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("v1/projectCpInfo")
public class ProjectCpInfoController {
    @Autowired
    IProjectCpDtlService projectCpDtlService;
    @Resource
    private RedisUtils redisUtils;
    /**
     * Project_Cp_Rpr成果办理的id
     * @param id
     * @return
     */
    @GetMapping("/handleProjectCpInfo")
    public BaseResponse handleProjectCpInfo(@RequestParam("id") String id,@RequestParam(value ="type",required = false) String type){
       if("1".equals(type)){

           return projectCpDtlService.dealWithNotPlan(id);
       }else{
           return projectCpDtlService.dealWith(id);
       }
    }

    @GetMapping("/cancelProjectCpInfo")
    public BaseResponse cancelProjectCpInfo(@RequestParam("cpdId") String cpdId, @RequestParam("cprId") String cprId){
        return projectCpDtlService.cancelProjectCpInfo(cpdId, cprId);
    }

    @PostMapping("/createProjectCpInfo")
    public BaseResponse createProjectCpInfo(@RequestBody ProjectCpDtl projectCpDtl){
        return projectCpDtlService.createProjectCpInfo(projectCpDtl);
    }

    @PostMapping("/getProjectInfo")
    public BaseResponse getProjectInfo(@RequestBody ProjectCpInfoRequest projectCpInfoRequest,@RequestHeader String token){
        LogUserInfoVo user= redisUtils.getUserInfoByToken(token);
        projectCpInfoRequest.setUId(user.getId());
        projectCpInfoRequest.setUType(user.getUType());
        return projectCpDtlService.getInfo(projectCpInfoRequest);

    }
}
