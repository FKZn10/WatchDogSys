package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditChangeLogModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import com.jpmsoft.projectConsumer.request.investment.SettlementFirstAuditRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/settlementFirstAudit")
public class SettlementFirstAuditController {

    @Autowired
    private InvFeign invFeign;
    /**
     * 保存或修改年度项目资金预设
     * @param SettlementFirstAuditModel
     * @return
     */
    @PostMapping("/saveOrUpdateSettlementFirstAudit")
    public BaseResponse saveOrUpdateSettlementFirstAudit(@RequestBody SettlementFirstAuditModel request, @RequestHeader String token){
        return invFeign.saveOrUpdateSettlementFirstAudit(request,token);
    }
    @PostMapping("/getSettlementFirstAuditClassInfo")
    public BaseResponse getSettlementFirstAuditClassInfo(@RequestBody SettlementFirstAuditRequest request){
        return invFeign.getSettlementFirstAuditClassInfo(request);
    }
    @PostMapping("/getSettlementFirstAuditList")
    public BaseResponse getSettlementFirstAuditList(@RequestBody SettlementFirstAuditRequest request){
        return invFeign.getSettlementFirstAuditList(request);
    }
    @PostMapping("/loadContractInfo")
    public BaseResponse loadContractInfo(@RequestBody SettlementFirstAuditModel request){
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return invFeign.loadContractInfo(request);
    }
    /**
     * id查询年度项目资金预设
     * @param
     * @return
     */
    @GetMapping("/getSettlementFirstAuditId")
    public BaseResponse getSettlementFirstAuditId(@RequestParam String id){
        return invFeign.getSettlementFirstAuditId(id);
    }
    @PostMapping("/saveOrUpdateSettlementFirstAuditChangeLog")
    public BaseResponse saveOrUpdateSettlementFirstAuditChangeLog(@RequestBody SettlementFirstAuditChangeLogModel request, @RequestHeader String token){
        return invFeign.saveOrUpdateSettlementFirstAuditChangeLog(request,token);
    }
    @PostMapping("/getSettlementFirstAuditChangeLogList")
    public BaseResponse getSettlementFirstAuditChangeLogList(@RequestBody SettlementFirstAuditModel request){
        return invFeign.getSettlementFirstAuditChangeLogList(request);
    }
    @GetMapping("/getSettlementFirstAuditChangeLogId")
    public BaseResponse getSettlementFirstAuditChangeLogId(@RequestParam String id){
        return invFeign.getSettlementFirstAuditChangeLogId(id);
    }
    @PostMapping("/approveChangeLogData")
    public BaseResponse approveChangeLogData(@RequestBody SettlementFirstAuditChangeLogModel request){
        return invFeign.approveChangeLogData(request);
    }
    @PostMapping("/deleteSettlementFirstAudit")
    public BaseResponse deleteSettlementFirstAudit(@RequestBody SettlementFirstAuditModel request){
        return invFeign.deleteSettlementFirstAudit(request);
    }
}
