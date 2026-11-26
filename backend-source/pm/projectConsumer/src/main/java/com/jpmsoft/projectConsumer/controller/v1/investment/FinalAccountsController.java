package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import com.jpmsoft.projectConsumer.pojo.vo.contract.FinalAccountingApprovalChangeVo;
import com.jpmsoft.projectConsumer.pojo.vo.contract.FinalAccountingApprovalVo;
import com.jpmsoft.projectConsumer.pojo.vo.contract.FinalAccountingReportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/finalAccounts")
@Api(tags = "决算管理(城建)", value = "决算管理(城建)")
public class FinalAccountsController {
    @Autowired
    private InvFeign invFeign;

    //查询
    @PostMapping("/getFinalAccountsList")
    @ApiOperation(value = "决算行文上报-查询列表")
    public BaseResponse getFinalAccountsList(@RequestBody FinalAccountingReportVo request, @RequestHeader String token){
        return invFeign.getFinalAccountsList(request,token);
    }

    //保存或修改
    @PostMapping("/createOrUpdateFinalAccounts")
    @ApiOperation(value = "决算行文上报-新增或修改")
    public BaseResponse createOrUpdateFinalAccounts(@RequestBody FinalAccountingReportVo request, @RequestHeader String token){
        return invFeign.createOrUpdateFinalAccounts(request,token);
    }

    //详情
    @GetMapping("/getFinalAccountsById")
    @ApiOperation(value = "决算行文上报-详情")
    public BaseResponse getFinalAccountsById(@RequestParam String id){
        return invFeign.getFinalAccountsById(id);
    }

    //新增时选择项目查询出概算分解数据
    @GetMapping("/getInvestmentBudgetByPId")
    @ApiOperation(value = "决算行文上报-新增时选择项目查询出概算分解数据")
    public BaseResponse getInvestmentBudgetByPId(@RequestParam String pId){
        return invFeign.getInvestmentBudgetByPId(pId);
    }

    //删除
    @GetMapping("/deleteFinalAccountsById")
    @ApiOperation(value = "决算行文上报-根据id删除")
    public BaseResponse deleteFinalAccountsById(@RequestParam String id){
        return invFeign.deleteFinalAccountsById(id);
    }

    //多条件查询
    @PostMapping("/queryFinalAccountsClassInfo")
    @ApiOperation(value = "决算行文上报-多条件列表查询")
    public BaseResponse queryFinalAccountsClassInfo(@RequestBody FinalAccountingReportVo request){
        return invFeign.queryFinalAccountsClassInfo(request);
    }

    //--------------------- 决算批复

    //查询列表
    @PostMapping("/getfinalApprovalList")
    @ApiOperation(value = "决算批复-查询列表")
    public BaseResponse getfinalApprovalList(@RequestBody FinalAccountingApprovalVo request){
        return  invFeign.getfinalApprovalList(request);
    }

    //详情
    @GetMapping("/getfinalApprovalById")
    @ApiOperation(value = "决算批复-根据id查询详情")
    public BaseResponse getfinalApprovalById(@RequestParam String id){
        return  invFeign.getfinalApprovalById(id);
    }

    //删除
    @GetMapping("/delFinalApprovalById")
    @ApiOperation(value = "决算批复-根据id删除")
    public BaseResponse delFinalApprovalById(@RequestParam String id){
        return  invFeign.delFinalApprovalById(id);
    }

    //新增或修改
    @PostMapping("/createOrUpdatefinalApproval")
    @ApiOperation(value = "决算批复-新增或修改")
    public BaseResponse createOrUpdatefinalApproval(@RequestBody FinalAccountingApprovalVo request,@RequestHeader String token){
        return  invFeign.createOrUpdatefinalApproval(request,token);
    }

    //决算批复多条件查询
    @ApiOperation(value = "决算批复-多条件查询")
    @PostMapping("/queryApprovalListClassInfo")
    public BaseResponse queryApprovalListClassInfo(@RequestBody FinalAccountingApprovalVo request){
        return invFeign.queryApprovalListClassInfo(request);
    }


    //变变保存或修改
    @PostMapping("/createOrUpdatefinalApprovalChange")
    @ApiOperation(value = "决算批复-变变保存或修改")
    public BaseResponse createOrUpdatefinalApprovalChange(@RequestBody FinalAccountingApprovalChangeVo request, @RequestHeader String token){
        return  invFeign.createOrUpdatefinalApprovalChange(request,token);
    }
    //变变查看详情
    @GetMapping("/getfinalApprovalChangeById")
    @ApiOperation(value = "决算批复-变变查看详情")
    public BaseResponse getfinalApprovalChangeById(@RequestParam String id){
        return  invFeign.getfinalApprovalChangeById(id);
    }

    //变更审批通过后吧数据修改到主表
    @GetMapping("/changeFinalApproval")
    @ApiOperation(value = "决算批复-变更审批通过同步数据到主表")
    public BaseResponse changeFinalApproval(@RequestParam String id ){
        return  invFeign.changeFinalApproval(id);
    }

    //变更记录
    @PostMapping("/getfinalApprovalChangeList")
    @ApiOperation(value = "决算批复-变更记录列表")
    public BaseResponse getfinalApprovalChangeList(@RequestBody FinalAccountingApprovalChangeVo request, @RequestHeader String token){
        return  invFeign.getfinalApprovalChangeList(request,token);
    }

}
