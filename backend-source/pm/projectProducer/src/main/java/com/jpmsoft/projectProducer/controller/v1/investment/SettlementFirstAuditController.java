package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditChangeLogModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.pojo.request.investment.SettlementFirstAuditRequest;
import com.jpmsoft.projectProducer.service.investment.ISettlementFirstAuditService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/settlementFirstAudit")
public class SettlementFirstAuditController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ISettlementFirstAuditService settlementFirstAuditService;
    @PostMapping("/saveOrUpdateSettlementFirstAudit")
    public BaseResponse saveOrUpdateSettlementFirstAudit(@RequestBody SettlementFirstAuditModel request, @RequestHeader String token){
        return settlementFirstAuditService.saveOrUpdateSettlementFirstAudit(request,redisUtils.getUid(token));
    }
    @PostMapping("/getSettlementFirstAuditClassInfo")
    public BaseResponse getSettlementFirstAuditClassInfo(@RequestBody SettlementFirstAuditRequest request){
        return settlementFirstAuditService.getSettlementFirstAuditClassInfo(request);
    }
    @PostMapping("/getSettlementFirstAuditList")
    public BaseResponse getSettlementFirstAuditList(@RequestBody SettlementFirstAuditRequest request){
        return settlementFirstAuditService.getSettlementFirstAuditList(request);
    }

    /**
     * id查询年度项目资金预设
     * @param
     * @return
     */
    @GetMapping("/getSettlementFirstAuditId")
    public BaseResponse getSettlementFirstAuditId(@RequestParam String id){
        return settlementFirstAuditService.getSettlementFirstAuditId(id);
    }

    @PostMapping("/loadContractInfo")
    public BaseResponse loadContractInfo(@RequestBody SettlementFirstAuditModel request){
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return settlementFirstAuditService.loadContractInfo(request);
    }
    @PostMapping("/saveOrUpdateSettlementFirstAuditChangeLog")
    public BaseResponse saveOrUpdateSettlementFirstAuditChangeLog(@RequestBody SettlementFirstAuditChangeLogModel request, @RequestHeader String token){
        return settlementFirstAuditService.saveOrUpdateSettlementFirstAuditChangeLog(request,redisUtils.getUid(token));
    }
    @PostMapping("/getSettlementFirstAuditChangeLogList")
    public BaseResponse getSettlementFirstAuditChangeLogList(@RequestBody SettlementFirstAuditModel request){
        return settlementFirstAuditService.getSettlementFirstAuditChangeLogList(request);
    }
    @GetMapping("/getSettlementFirstAuditChangeLogId")
    public BaseResponse getSettlementFirstAuditChangeLogId(@RequestParam String id){
        return settlementFirstAuditService.getSettlementFirstAuditChangeLogId(id);
    }
    @PostMapping("/approveChangeLogData")
    public BaseResponse approveChangeLogData(@RequestBody SettlementFirstAuditChangeLogModel request){
        return settlementFirstAuditService.approveChangeLogData(request);
    }

    @PostMapping("/deleteSettlementFirstAudit")
    public BaseResponse deleteSettlementFirstAudit(@RequestBody SettlementFirstAuditModel request){
        return settlementFirstAuditService.deleteSettlementFirstAudit(request);
    }
}
