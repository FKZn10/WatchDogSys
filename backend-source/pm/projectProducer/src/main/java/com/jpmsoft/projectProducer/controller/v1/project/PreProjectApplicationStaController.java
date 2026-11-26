package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectApplicationStaRequest;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//项目应用情况汇总
@Slf4j
@RestController
@RequestMapping("/v1/applicationSta")
public class PreProjectApplicationStaController {
    @Autowired
    private IPreProjectService iPreProjectService;
    @Autowired
    private RedisUtils redisUtils;


    //项目应用情况汇总， 列表
    @PostMapping("/getPreprojectStaList")
    public BaseResponse getPreprojectStaList(@RequestBody PreProjectApplicationStaRequest request, @RequestHeader String token){

       return iPreProjectService.getPreprojectStaList(request,redisUtils.getUid(token));
    }

    //项目应用情况汇总， 详情
    @PostMapping("/getPreprojectStaByPId")
    public BaseResponse getPreprojectStaByPId(@RequestBody PreProjectApplicationStaRequest request){
        return iPreProjectService.getPreprojectStaByPId(request);
    }

    //功能应用情况汇总  列表
    @PostMapping("/getApplicationStaList")
    public BaseResponse getApplicationStaList(@RequestBody PreProjectApplicationStaRequest request, @RequestHeader String token){
        return iPreProjectService.getApplicationStaList(request,redisUtils.getUid(token));
    }

    //功能应用情况汇总， 详情
    @PostMapping("/getApplicationStaByPId")
    public BaseResponse getApplicationStaByPId(@RequestBody PreProjectApplicationStaRequest request){
        return iPreProjectService.getApplicationStaByPId(request);
    }
}
