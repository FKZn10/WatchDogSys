package com.jpmsoft.projectProducer.controller.v1.project;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectOrgStructRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectOrgStructModel;
import com.jpmsoft.projectProducer.service.project.IPreProjectOrgStructService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目组织机构
 *
 * @author c
 * @since 2024-12-11 17:39:08
 */
@Slf4j
@RestController
@RequestMapping("/v1/preProjectOrgStruct")
public class PreProjectOrgStructController {

    @Resource
    private IPreProjectOrgStructService preProjectOrgStructService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/syncPreProjectOrgStruct")
    public BaseResponse syncPreProjectOrgStruct(@RequestBody PreProjectOrgStructModel request,@RequestHeader String token){
        log.info("syncPreProjectOrgStruct");
      return  preProjectOrgStructService.syncPreProjectOrgStruct(request,redisUtils.getUid(token));
    }

    @PostMapping("/getPreProjectOrgStructList")
    public BaseResponse getPreProjectOrgStructList(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token){
        log.info("getPreProjectOrgStructList");
        String uid = redisUtils.getUid(token);
        return  preProjectOrgStructService.getPreProjectOrgStructList(request,uid);
    }



    @GetMapping("/getPreProjectOrgStructById")
    public BaseResponse getPreProjectOrgStructById(@RequestParam String id){
        log.info("getPreProjectOrgStructById");
        return  preProjectOrgStructService.getPreProjectOrgStructById(id);
    }

    @GetMapping("/getPreProjectOrgStructChangeById")
    public BaseResponse getPreProjectOrgStructChangeById(@RequestParam String id){
        log.info("getPreProjectOrgStructChangeById");
        return  preProjectOrgStructService.getPreProjectOrgStructChangeById(id);
    }



    //同步组织人员  不使用
    @PostMapping("/saveAndsynchronouSorganization")
    public BaseResponse synchronouSorganization(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token){
        return preProjectOrgStructService.saveAndsynchronouSorganization(request,redisUtils.getUid(token));
    }

    //维护组织人员
    @PostMapping("/changesynchronouSorganization")
    public BaseResponse changesynchronouSorganization(@RequestBody PreProjectOrgStructRequest request, @RequestHeader String token){
        return preProjectOrgStructService.changesynchronouSorganization(request,redisUtils.getUid(token));
    }

    //查询变更记录
    @PostMapping("/getchangeRecords")
    public BaseResponse getchangeRecords(@RequestBody PreProjectOrgStructRequest request){
        return  preProjectOrgStructService.getchangeRecords(request);
    }


    //流程审批结束后，保存到到组织结构
    @GetMapping("/saveProjectOrganization")
    public BaseResponse  saveProjectOrganization(@RequestParam  String id ){
        return  preProjectOrgStructService.saveProjectOrganization(id);
    }

    //查看该项目是否有招标计划并且有监理合同
    @GetMapping("/queryInspectionContract")
    public BaseResponse queryInspectionContract(@RequestParam String pId){
        return  preProjectOrgStructService.queryInspectionContract(pId);
    }

    //================================================= 项目组织机构

    //获取项目组织机构人员详情
    @GetMapping("getProjectOrganizationById")
    public BaseResponse getProjectOrganizationById(@RequestParam String pId){
        return  preProjectOrgStructService.getProjectOrganizationById(pId);
    }




}

