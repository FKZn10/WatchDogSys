package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecContractSignPleaseReviewRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 合同签订请审
 *
 */
@RestController
@RequestMapping("v1/contractSignPleaseReview")
@Slf4j
public class SecContractSignPleaseReviewController {

    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建修改合同签订请审")
    @PostMapping("/createOrUpdateContractSignPleaseReview")
    public BaseResponse createOrUpdateContractSignPleaseReview(@RequestBody SecContractSignPleaseReviewRequest request) {
        log.info("createOrUpdateContractSignPleaseReview");
        return secFeign.createOrUpdateContractSignPleaseReview(request);
    }

    @ApiOperation(value = "id删除合同签订请审")
    @GetMapping("/deleteContractSignPleaseReviewById")
    public BaseResponse deleteContractSignPleaseReviewById(@RequestParam String csprId) {
        log.info("deleteContractSignPleaseReviewById");
        return secFeign.deleteContractSignPleaseReviewById(csprId);
    }

    @ApiOperation(value = "id查询合同签订请审")
    @GetMapping("/getContractSignPleaseReviewById")
    public BaseResponse getContractSignPleaseReviewById(@RequestParam String csprId) {
        log.info("getContractSignPleaseReviewById");
        return secFeign.getContractSignPleaseReviewById(csprId);
    }

    @ApiOperation(value = "查询合同签订请审列表")
    @PostMapping("/getContractSignPleaseReviewList")
    public BaseResponse getContractSignPleaseReviewList(@RequestBody SecContractSignPleaseReviewRequest request) {
        log.info("getContractSignPleaseReviewList");
        return secFeign.getContractSignPleaseReviewList(request);
    }




}

