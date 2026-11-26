package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import com.jpmsoft.projectConsumer.pojo.vo.contract.SettlementAuditVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/settlement")
public class SettlementController {

    @Autowired
    private InvFeign invFeign;

    //保存或更新
    @PostMapping("/createOrUpdateSettAudit")
    public BaseResponse createOrUpdateSettAudit(@RequestBody SettlementAuditVo request, @RequestHeader String token){
        return invFeign.createOrUpdateSettAudit(request,token);
    }

    //删除
    @GetMapping("/deleteSettAuditById")
    public BaseResponse deleteSettAuditById(@RequestParam String id){
        return invFeign.deleteSettAuditById(id);
    }

    //查询列表接口
    @PostMapping("/querySettAuditList")
    public BaseResponse querySettAuditList(@RequestBody SettlementAuditVo request){
        return invFeign.querySettAuditList(request);
    }

    //查询子表详情
    @GetMapping("/getSettAuditDetailId")
    public BaseResponse getSettAuditDetailId(@RequestParam  String id){
        return invFeign.getSettAuditDetailId(id);
    }

    //获取合同初审金额
    @GetMapping("/getConReviewAmount")
    public BaseResponse getConReviewAmount(@RequestParam String conId){
        return invFeign.getConReviewAmount(conId);

    }

    @PostMapping("/querySettAuditListClassInfo")
    public BaseResponse querySettAuditListClassInfo(@RequestBody SettlementAuditVo request){
        return invFeign.querySettAuditListClassInfo(request);
    }



    @PostMapping("/changeSettAuditChange")
    public BaseResponse changeSettAuditChange(@RequestBody SettlementAuditVo request){
        return invFeign.changeSettAuditChange(request);
    }

    //流程结束后修改主表数据
    @GetMapping("/updateChangeSettAudit")
    public BaseResponse updateChangeSettAudit(@RequestParam String id){
        return invFeign.updateChangeSettAudit(id);
    }

    //查询变更记录
    @PostMapping("/querySettAuditChangeList")
    public BaseResponse querySettAuditChangeList(@RequestBody SettlementAuditVo request){
        return invFeign.querySettAuditChangeList(request);
    }

    //查询子表详情
    @GetMapping("/getSettAuditDetailChangeId")
    public BaseResponse getSettAuditDetailChangeId(@RequestParam  String id){
        return invFeign.getSettAuditDetailChangeId(id);
    }


}
