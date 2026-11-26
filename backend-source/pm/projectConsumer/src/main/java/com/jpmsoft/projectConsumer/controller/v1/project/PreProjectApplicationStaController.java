package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectApplicationStaRequest;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//项目应用情况汇总
@Slf4j
@RestController
@Api(tags = "项目情况汇总", value = "项目情况汇总")
@RequestMapping("/v1/applicationSta")
public class PreProjectApplicationStaController {
    @Resource
    private PreFeign preFeign;


    //项目应用情况汇总， 列表
    @ApiOperation(value = "项目应用情况汇总列表")
    @PostMapping("/getPreprojectStaList")
    public BaseResponse getPreprojectStaList(@RequestBody PreProjectApplicationStaRequest request, @RequestHeader String token){

       return preFeign.getPreprojectStaList(request,token);
    }

    //项目应用情况汇总， 详情
    @ApiOperation(value = "项目应用情况汇总详情")
    @PostMapping("/getPreprojectStaByPId")
    public BaseResponse getPreprojectStaByPId(@RequestBody PreProjectApplicationStaRequest request){
        return preFeign.getPreprojectStaByPId(request);
    }

    //功能应用情况汇总  列表
    @ApiOperation(value = "功能应用情况汇总列表")
    @PostMapping("/getApplicationStaList")
    public BaseResponse getApplicationStaList(@RequestBody PreProjectApplicationStaRequest request, @RequestHeader String token){
        return preFeign.getApplicationStaList(request,token);
    }

    //功能应用情况汇总， 详情
    @ApiOperation(value = "功能应用情况汇总详情")
    @PostMapping("/getApplicationStaByPId")
    public BaseResponse getApplicationStaByPId(@RequestBody PreProjectApplicationStaRequest request){
        return preFeign.getApplicationStaByPId(request);
    }
}
