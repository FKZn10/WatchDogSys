package com.jpmsoft.projectConsumer.controller.v1.project;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectOrgStructRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectOrgStructModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "项目组织机构", value = "项目组织机构")
@RequestMapping("/v1/preProjectOrgStruct")
public class PreProjectOrgStructController {

    @Resource
    private PreFeign preFeign;

    @PostMapping("/syncPreProjectOrgStruct")
    public BaseResponse syncPreProjectOrgStruct(@RequestBody PreProjectOrgStructModel request,@RequestHeader String token){
      return  preFeign.syncPreProjectOrgStruct(request,token);
    }

    @PostMapping("/getPreProjectOrgStructList")
    public BaseResponse getPreProjectOrgStructList(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token){
        log.info("getPreProjectOrgStructList");
        return  preFeign.getPreProjectOrgStructList(request,token);
    }

    @GetMapping("/getPreProjectOrgStructById")
    public BaseResponse getPreProjectOrgStructById(@RequestParam String id){
        log.info("getPreProjectOrgStructById");
        return  preFeign.getPreProjectOrgStructById(id);
    }

    @GetMapping("/getPreProjectOrgStructChangeById")
    public BaseResponse getPreProjectOrgStructChangeById(@RequestParam String id){
        log.info("getPreProjectOrgStructChangeById");
        return  preFeign.getPreProjectOrgStructChangeById(id);
    }


    //项目组织机构保存并同步到后台
    @PostMapping("/saveAndsynchronouSorganization")
    public BaseResponse saveAndsynchronouSorganization(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token){
        log.info("saveAndsynchronouSorganization");
        return  preFeign.saveAndsynchronouSorganization(request,token);
    }

    //变更
    @PostMapping("/changesynchronouSorganization")
    public BaseResponse changesynchronouSorganization(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token){
        log.info("changesynchronouSorganization");
        return  preFeign.changesynchronouSorganization(request,token);
    }

    @GetMapping("/queryInspectionContract")
    public BaseResponse queryInspectionContract(@RequestParam String pId){
        log.info("queryInspectionContract");
        return  preFeign.queryInspectionContract(pId);
    }




    //查询变更记录
    @PostMapping("/getchangeRecords")
    public BaseResponse getchangeRecords(@RequestBody PreProjectOrgStructRequest request){
        log.info("getchangeRecords");
        return  preFeign.getchangeRecords(request);
    }

    //流程审批结束，保存列表到项目组织机构
    @GetMapping("/saveProjectOrganization")
    public BaseResponse  saveProjectOrganization(@RequestParam  String id ){
        log.info("saveProjectOrganization");
        return  preFeign.saveProjectOrganization(id);
    }




}

