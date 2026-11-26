package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalChangeVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportVo;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingApprovalChangeService;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingApprovalService;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingReportService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//决算管理
@Slf4j
@RestController
@RequestMapping("/v1/finalAccounts")
public class FinalAccountsController {

    @Autowired
    private FinalAccountingReportService finalAccountingReportService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private FinalAccountingApprovalService finalAccountingApprovalService;

    @Autowired
    private FinalAccountingApprovalChangeService finalAccountingApprovalChangeService;


    //查询列表
    @PostMapping("/getFinalAccountsList")
    public BaseResponse getFinalAccountsList(@RequestBody FinalAccountingReportVo request, @RequestHeader String token){
        return finalAccountingReportService.getFinalAccountsList(request,redisUtils.getUid(token));
    }

    //保存或修改
    @PostMapping("/createOrUpdateFinalAccounts")
    public BaseResponse createOrUpdateFinalAccounts(@RequestBody FinalAccountingReportVo request, @RequestHeader String token){
        return finalAccountingReportService.createOrUpdateFinalAccounts(request,redisUtils.getUid(token));
    }

    //详情
    @GetMapping("/getFinalAccountsById")
    public BaseResponse getFinalAccountsById(@RequestParam String id){
        return finalAccountingReportService.getFinalAccountsById(id);
    }


    //新增时选择项目查询出概算分解数据
    @GetMapping("/getInvestmentBudgetByPId")
    public BaseResponse getInvestmentBudgetByPId(@RequestParam String pId){
        return finalAccountingReportService.getInvestmentBudgetByPId(pId);
    }

    //删除
    @GetMapping("/deleteFinalAccountsById")
    public BaseResponse deleteFinalAccountsById(@RequestParam String id){
        return finalAccountingReportService.deleteFinalAccountsById(id);
    }

    //多条件查询
    @PostMapping("/queryFinalAccountsClassInfo")
    public BaseResponse queryFinalAccountsClassInfo(@RequestBody FinalAccountingReportVo request){
        return finalAccountingReportService.queryFinalAccountsClassInfo(request);
    }

    //--------------------- 决算批复

    //查询列表
    @PostMapping("/getfinalApprovalList")
    public BaseResponse getfinalApprovalList(@RequestBody FinalAccountingApprovalVo request){
        return  finalAccountingApprovalService.getfinalApprovalList(request);
    }

    //详情
    @GetMapping("/getfinalApprovalById")
    public BaseResponse getfinalApprovalById(@RequestParam String id){
        return  finalAccountingApprovalService.getfinalApprovalById(id);
    }

    //删除
    @GetMapping("/delFinalApprovalById")
    public BaseResponse delFinalApprovalById(@RequestParam String id){
        return  finalAccountingApprovalService.delFinalApprovalById(id);
    }

    //新增或修改
    @PostMapping("/createOrUpdatefinalApproval")
    public BaseResponse createOrUpdatefinalApproval(@RequestBody FinalAccountingApprovalVo request,@RequestHeader String token){
        return  finalAccountingApprovalService.createOrUpdatefinalApproval(request,redisUtils.getUid(token));
    }

    //决算批复多条件查询
    @PostMapping("/queryApprovalListClassInfo")
    public BaseResponse queryApprovalListClassInfo(@RequestBody FinalAccountingApprovalVo request){
        return finalAccountingApprovalService.queryApprovalListClassInfo(request);
    }

    //变变保存或修改
    @PostMapping("/createOrUpdatefinalApprovalChange")
    public BaseResponse createOrUpdatefinalApprovalChange(@RequestBody FinalAccountingApprovalChangeVo request, @RequestHeader String token){
        return  finalAccountingApprovalChangeService.createOrUpdatefinalApprovalChange(request,redisUtils.getUid(token));
    }
    //变变查看详情
    @GetMapping("/getfinalApprovalChangeById")
    public BaseResponse getfinalApprovalChangeById(@RequestParam String id){
        return  finalAccountingApprovalChangeService.getfinalApprovalChangeById(id);
    }

    //变更审批通过后吧数据修改到主表
    @GetMapping("/changeFinalApproval")
    public BaseResponse changeFinalApproval(@RequestParam String id ){
        return  finalAccountingApprovalChangeService.changeFinalApproval(id);
    }

    //变更记录
    @PostMapping("/getfinalApprovalChangeList")
    public BaseResponse getfinalApprovalChangeList(@RequestBody FinalAccountingApprovalChangeVo request,@RequestHeader String token){
        return  finalAccountingApprovalChangeService.getfinalApprovalChangeList(request,redisUtils.getUid(token));
    }





}
