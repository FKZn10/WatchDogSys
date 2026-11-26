package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 支付报审清单
 *
 * @Author: 李文涛 aaron
 * @date: 2024/10/17 14:11
 */
@Slf4j
@RestController
@Api(tags = "支付报审清单", value = "支付报审清单")
@RequestMapping("/v1/conPayMeasure")
public class ConPayMeasureController {
    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增修改支付报审清单（计量单ID集合，合同id,支付ID）")
    @PostMapping("/createOrUpdateConPayMeasure")
    public BaseResponse createOrUpdateConPayMeasure(@RequestBody ConPayRequest request) {
        log.info("createOrUpdateConPayMeasure");
        return   conFeign.createOrUpdateConPayMeasure(request);
    }


    /**
     * 查询合同下可支付报审的计量单
     *
     * @param conId 合同ID
     * @return
     */
    @ApiOperation(value = "查询合同下可支付报审的计量单")
    @GetMapping("/getUnMeasureApplyAndAmtByConId")
    public BaseResponse getUnMeasureApplyAndAmtByConId(@RequestParam String conId) {
        log.info("getUnMeasureApplyAndAmtByConId");
        return conFeign.getUnMeasureApplyAndAmtByConId(conId);
    }

    /**
     * 查询合同支付下的支付报审的计量单
     *
     * @param payId 支付ID
     * @return
     */
    @ApiOperation(value = "查询合同支付下的支付报审的计量单")
    @GetMapping("/getApplyAndAmtByPayId")
    public BaseResponse getApplyAndAmtByPayId(@RequestParam String payId) {
        log.info("getApplyAndAmtByPayId");
        return conFeign.getApplyAndAmtByPayId(payId);
    }
}
