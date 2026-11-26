package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMeasure;
import com.jpmsoft.projectProducer.service.contract.IConPayMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 支付报审清单
 * @Author: 李文涛 aaron
 * @date: 2024/10/17 14:11
 */
@Slf4j
@RestController
@RequestMapping("/v1/conPayMeasure")
public class ConPayMeasureController {
    @Resource
    private IConPayMeasureService conPayMeasureService;


    @PostMapping("/createOrUpdateConPayMeasure")
    public BaseResponse createOrUpdateConPayMeasure(@RequestBody ConPayRequest request) {
        log.info("createOrUpdateConPayMeasure");
        conPayMeasureService.createOrUpdate(request.getMeaIds(),request.getConId(),request.getPayId());
        return BaseResponse.ok();
    }

    /**
     * 查询合同下可支付报审的计量单
     * @param conId 合同ID
     * @return
     */
    @GetMapping("/getUnMeasureApplyAndAmtByConId")
    public BaseResponse getUnMeasureApplyAndAmtByConId(@RequestParam String conId) {
        log.info("getUnMeasureApplyAndAmtByConId");
        return conPayMeasureService.getUnMeasureApplyAndAmtByConId(conId);
    }
    /**
     * 查询合同支付下的支付报审的计量单
     *
     * @param payId 支付ID
     * @return
     */
    @GetMapping("/getApplyAndAmtByPayId")
    public BaseResponse getApplyAndAmtByPayId(@RequestParam String payId) {
        log.info("getApplyAndAmtByPayId");
        return conPayMeasureService.getApplyAndAmtByPayId(payId);
    }
}
