package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRelationListRequest;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.service.contract.IConEcFeeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEcFee")
public class ConEcFeeController {
    @Resource
    private IConEcFeeService conEcFeeService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConEcFee")
    public BaseResponse createOrUpdateConEcFee(@RequestBody ConEcFeeRequest request) {
        log.info("createOrUpdateConEcFee");
        return conEcFeeService.createOrUpdateConEcFee(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateConEcFeeOrRecord")
    public BaseResponse createOrUpdateConEcFeeOrRecord(@RequestBody ConEcFeeRequest request) {
        log.info("createOrUpdateConEcFeeOrRecord");
        return conEcFeeService.createOrUpdateConEcFeeOrRecord(request);
    }

    @TenantId
    @PostMapping("/getConEcFeeList")
    public BaseResponse getConEcFeeList(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeList");
        return conEcFeeService.getConEcFeeList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcFeeRelationList")
    public BaseResponse getConEcFeeRelationList(@RequestBody ConEcFeeRelationListRequest request,
                                                @RequestHeader("token") String token) {
        log.info("getConEcFeeRelationList");
        return conEcFeeService.getConEcFeeRelationList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcFeeListorRecord")
    public BaseResponse getConEcFeeListorRecord(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeList");
        return conEcFeeService.getConEcFeeListorRecord(request, redisUtils.getUid(token));
    }


    @PostMapping("/getConEcFeeClassInfo")
    public BaseResponse getConEcFeeClassInfo(@RequestBody ConEcFeeRequest request,@RequestHeader String token) {
        log.info("getConEcFeeClassInfo");
        return conEcFeeService.getConEcFeeClassInfo(request,redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcFeeStandingBook")
    public BaseResponse getConEcFeeStandingBook(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeStandingBook");
        return conEcFeeService.getConEcFeeStandingBook(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcFeeListNoAuth")
    public BaseResponse getConEcFeeListNoAuth(@RequestBody ConEcFeeRequest request) {
        log.info("getConEcFeeListNoAuth");
        return conEcFeeService.getConEcFeeListNoAuth(request);
    }

    @PostMapping("/getConEcFeeByEcfId")
    public BaseResponse getConEcFeeByEcfId(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeByEcfId");
        return conEcFeeService.getConEcFeeByEcfId(request, redisUtils.getUid(token));
    }

    @PostMapping("/getConEcFeeorRecordByEcfId")
    public BaseResponse getConEcFeeorRecordByEcfId(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeorRecordByEcfId");
        return conEcFeeService.getConEcFeeorRecordByEcfId(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConEcFeeApplyMoneySum")
    public BaseResponse getConEcFeeApplyMoneySum(@RequestParam String conId) {
        log.info("getConEcFeeApplyMoneySum");
        return conEcFeeService.getConEcFeeApplyMoneySum(conId);
    }


    /********************************************清标********************************************/

    @TenantId
    @PostMapping("/createOrUpdateConEcFeeClear")
    public BaseResponse createOrUpdateConEcFeeClear(@RequestBody ConEcFeeRequest request) {
        log.info("createOrUpdateConEcFeeClear");
        return conEcFeeService.createOrUpdateConEcFeeClear(request);
    }

    @TenantId
    @PostMapping("/getConEcFeeClearList")
    public BaseResponse getConEcFeeClearList(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeClearList");
        return conEcFeeService.getConEcFeeClearList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConEcFeeClearById")
    public BaseResponse getConEcFeeClearById(@RequestParam String id) {
        log.info("getConEcFeeClearById");
        return conEcFeeService.getConEcFeeClearById(id);
    }

    @GetMapping("/getPreliminaryAmountSumByConId")
    public BaseResponse getPreliminaryAmountSumByConId(@RequestParam("conId") String conId) {
        log.info("getPreliminaryAmountSumByConId");
        return conEcFeeService.getPreliminaryAmountSumByConId(conId);
    }

    // 查询变更支付记录
    @PostMapping("/getConEcFeeChangePayList")
    public BaseResponse getConEcFeeChangePayList(@RequestBody ConEcFeeRequest request, @RequestHeader String token) {
        log.info("getConEcFeeChangePayList");
        return conEcFeeService.getConEcFeeChangePayList(request, redisUtils.getUid(token));
    }
}
