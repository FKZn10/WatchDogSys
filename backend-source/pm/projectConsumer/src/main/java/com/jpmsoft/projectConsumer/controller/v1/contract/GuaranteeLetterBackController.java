package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterBackRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "保函办退", value = "保函办退")
@RequestMapping("/guarantee/v1/GuaranteeLetter")
public class GuaranteeLetterBackController {
    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改保函办退")
    @PostMapping("/createOrUpdateGuaranteeLetterBack")
    public BaseResponse createOrUpdateGuaranteeLetterBack(@RequestBody GuaranteeLetterBackRequest request) {
        log.info("createOrUpdateGuaranteeLetterBack");
        return conFeign.createOrUpdateGuaranteeLetterBack(request);
    }

    @ApiOperation(value = "分页查询保函办退")
    @PostMapping("/getGuaranteeLetterBackList")
    public BaseResponse getGuaranteeLetterBackList(@RequestBody GuaranteeLetterBackRequest request, @RequestHeader String token) {
        log.info("getGuaranteeLetterBackList");
        return conFeign.getGuaranteeLetterBackList(request, token);
    }

    @ApiOperation(value = "ID查询保函办退")
    @GetMapping("/getGuaranteeLetterBackByglbId")
    public BaseResponse getGuaranteeLetterBackByglbId(@RequestParam String glbId) {
        log.info("getGuaranteeLetterBackByglbId");
        return conFeign.getGuaranteeLetterBackByglbId(glbId);
    }

    @ApiOperation(value = "ID查询保函办退和签收")
    @GetMapping("/getGuaranteeLetterBackAndSignByglbId")
    public BaseResponse getGuaranteeLetterBackAndSignByglbId(@RequestParam String glbId) {
        log.info("getGuaranteeLetterBackAndSignByglbId");
        return conFeign.getGuaranteeLetterBackAndSignByglbId(glbId);
    }
}
